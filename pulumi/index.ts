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


const network = new gcp.compute.Network("network")

const firewall = new gcp.compute.Firewall("firewall", {
    network: network.id,
    allows: [
        {protocol: "tcp", ports: ["22"],},
        {protocol: "tcp", ports: ["80"],},
        {protocol: "tcp", ports: ["8080"],},
    ]
})

const container_instance_metadata_script = `
spec:
    containers:
        - name: c1
          image: 'gcr.io/cloud-marketplace/google/nginx1:latest'
          stdin: false
          tty: false
    restartPolicy: Always
`

const instanceTemplate = new gcp.compute.InstanceTemplate("template-1", {
    name: "template-1",
    disks: [{
        autoDelete: true,
        boot: true,
        sourceImage: "cos-cloud/cos-85-lts",
    }],
    machineType: "e2-small",
    scheduling: {
        preemptible: true,
        automaticRestart: false,
    },
    networkInterfaces: [{
        network: network.id,
    }],
    metadata: {
        "gce-container-declaration": container_instance_metadata_script,
    },
    serviceAccount: {
        email: "default",
        scopes: [
            "https://www.googleapis.com/auth/devstorage.read_only",
            "https://www.googleapis.com/auth/logging.write",
            "https://www.googleapis.com/auth/monitoring.write",
            "https://www.googleapis.com/auth/service.management.readonly",
            "https://www.googleapis.com/auth/servicecontrol",
            "https://www.googleapis.com/auth/trace.append",
        ],
    },
    // metadataStartupScript: fs.readFileSync(`${__dirname}/files/startup.sh`, "utf-8"),
    // labels: options.labels,
    // tags: options.tags,
});

const targetPool = new gcp.compute.TargetPool("client-pool", {});
const instanceGroupManager = new gcp.compute.InstanceGroupManager("instance-group-manager", {
    baseInstanceName: "be-instance",
    versions: [{
        instanceTemplate: instanceTemplate.id,
        name: "live"
    }],
    waitForInstances: true,
    namedPorts: [
        {
            name: "app",
            port: 8080,
        },
        {
            name: "nginx",
            port: 80,
        }
    ],
    targetPools: [targetPool.id],
    // statefulDisks: {},
    targetSize: 0,
    zone: "europe-west3-a"
});

const healthCheck = new gcp.compute.HealthCheck("health-check", {
    httpHealthCheck: {
        port: 8080,
        // requestPath: "/health",
        requestPath: "/",
    },
    checkIntervalSec: 1,
    timeoutSec: 1,
});

const groupBackend = new gcp.compute.BackendService("group-backend", {
    // portName: "app",
    portName: "nginx",
    protocol: "HTTP",
    timeoutSec: 5,
    healthChecks: healthCheck.id,
    backends: [{
        group: instanceGroupManager.instanceGroup,
    }],
});

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
            defaultService: groupBackend.id
            // defaultUrlRedirect: {
            //     hostRedirect: "google.com",
            //     stripQuery: true
            // },
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
