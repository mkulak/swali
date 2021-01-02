import * as pulumi from "@pulumi/pulumi";
import * as gcp from "@pulumi/gcp";


const region = "europe-west3"
let host = "swali.kvarto.net";

const staticSiteBucket = new gcp.storage.Bucket("fe-static", {
    name: "fe-static",
    cors: [{
        maxAgeSeconds: 3600,
        methods: [
            "GET",
            "HEAD",
            "PUT",
            "POST",
            "DELETE",
        ],
        origins: ["*"],
        responseHeaders: ["*"],
    }],
    forceDestroy: true,
    location: region,
    storageClass: "REGIONAL",
    uniformBucketLevelAccess: true,
    website: {
        mainPageSuffix: "index.html",
        notFoundPage: "404.html",
    },
});

const binding = new gcp.storage.BucketIAMBinding("memberSource", {
    bucket: staticSiteBucket.name,
    members: ["allUsers"],
    role: "roles/storage.objectViewer",
});

const ipAddress = new gcp.compute.GlobalAddress("site-lb-ip", {
        // region: region,
        name: "site-lb-ip",
        addressType: 'EXTERNAL',
    },
)

const siteBackend = new gcp.compute.BackendBucket("site-backend-bucket", {
    description: "",
    bucketName: staticSiteBucket.name,
    enableCdn: true,
});

const urlMap = new gcp.compute.URLMap("site-lb", {
    description: "",
    name: 'site-lb',
    defaultService: siteBackend.id,
    hostRules: [{
        hosts: [host],
        pathMatcher: "allpaths",
    }],
    pathMatchers: [{
        name: "allpaths",
        defaultService: siteBackend.id,
        pathRules: [{
            paths: ["/*"],
            service: siteBackend.id,
        }],
    }],
});

const sslCertificate = new gcp.compute.ManagedSslCertificate("swali-certificate", {
    name: "swali-certificate",
    managed: {domains: [host],},
    type: "MANAGED",
});

const proxy = new gcp.compute.TargetHttpsProxy("site-lb-target-proxy", {
    name: "site-lb-target-proxy",
    urlMap: urlMap.id,
    sslCertificates: [sslCertificate.id],
});


const forwardingRule = new gcp.compute.GlobalForwardingRule("site-fe", {
    name: 'site-fe',
    portRange: '443-443',
    target: proxy.id,
    ipAddress: ipAddress.address,
});


export const bucketName = staticSiteBucket.url;
export const ip = ipAddress.address;
export const urlMapId = urlMap.id;
export const sslCertId = sslCertificate.id;
