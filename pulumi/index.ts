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


// const targetPool = new gcp.compute.TargetPool("client-pool", {});

const instanceTemplate = new gcp.compute.InstanceTemplate("template-1", {
    disks: [{
        autoDelete: true,
        boot: true,
        sourceImage: "debian-9",
    }],
    // labels: options.labels,
    machineType: "e2-small",
    scheduling: {
        preemptible: true,
        automaticRestart: false,
    },

    // metadataStartupScript: fs.readFileSync(`${__dirname}/files/startup.sh`, "utf-8"),
    networkInterfaces: [{
        network: "default",
    }],
    // serviceAccount: {
    //     email: `${options.serviceAccountName}@assetstore.iam.gserviceaccount.com`,
    //     scopes: ["compute-ro"],
    // },
    // tags: options.tags,
});

// const instanceGroupManager = new gcp.compute.InstanceGroupManager("instance-group-manager", {
//     baseInstanceName: "be-instance",
//     versions: [{
//         instanceTemplate: instanceTemplate.id,
//         name: "live"
//     }],
//     waitForInstances: true,
//     namedPorts: [{
//         name: "app",
//         port: 8080,
//     }],
//     targetPools: [targetPool.id],
//     // statefulDisks: {},
//     targetSize: 3,
// });

// const healthCheck = new gcp.compute.HttpHealthCheck("health-check", {
//     port: 8080,
//     requestPath: "/health",
//     checkIntervalSec: 1,
//     timeoutSec: 1,
// });

// const groupBackend = new gcp.compute.BackendService("group-backend", {
//     portName: "app",
//     protocol: "HTTP2",
//     timeoutSec: 5,
//     healthChecks: healthCheck.id,
//     backends: [{
//         group: instanceGroupManager.instanceGroup,
//     }],
// });

const urlMap = new gcp.compute.URLMap("site-lb", {
    description: "",
    name: 'site-lb',
    defaultService: siteBackend.id,
    hostRules: [
        {
            hosts: [host],
            pathMatcher: "swali",
        },
        {
            hosts: ["app.kvarto.net"],
            pathMatcher: "app",
        }
    ],
    pathMatchers: [
        {
            name: "swali",
            defaultService: siteBackend.id,
            pathRules: [{
                paths: ["/*"],
                service: siteBackend.id,
            }],
        },
        {
            name: "app",
            // defaultService: groupBackend.id
            defaultUrlRedirect: {
                hostRedirect: "google.com",
                stripQuery: true
            },
        },
    ],
});

const sslCertificate = new gcp.compute.ManagedSslCertificate("swali-certificate", {
    name: "swali-certificate",
    managed: {domains: [host],},
    type: "MANAGED",
});

const appSslCertificate = new gcp.compute.ManagedSslCertificate("app-certificate", {
    name: "app-certificate",
    managed: {domains: ["app.kvarto.net"],},
    type: "MANAGED",
});

const proxy = new gcp.compute.TargetHttpsProxy("site-lb-target-proxy", {
    name: "site-lb-target-proxy",
    urlMap: urlMap.id,
    sslCertificates: [sslCertificate.id, appSslCertificate.id],
});

const httpUrlMap = new gcp.compute.URLMap("http-map", {
    name: 'http-map',
    defaultUrlRedirect: {
        httpsRedirect: true,
        stripQuery: false,
    }
});

const httpProxy = new gcp.compute.TargetHttpProxy("http-proxy", {
    name: "http-proxy",
    urlMap: httpUrlMap.id,
});

const forwardingRule = new gcp.compute.GlobalForwardingRule("site-fe", {
    name: 'site-fe',
    portRange: '443-443',
    ipAddress: ipAddress.address,
    target: proxy.id,
});

const httpForwardingRule = new gcp.compute.GlobalForwardingRule("http-to-https", {
    name: 'http-to-https',
    portRange: '80',
    ipAddress: ipAddress.address,
    target: httpProxy.id,
});


export const bucketName = staticSiteBucket.url;
export const ip = ipAddress.address;
// export const urlMapId = urlMap.id;
// export const sslCertId = sslCertificate.id;
