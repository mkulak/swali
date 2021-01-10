import * as gcp from "@pulumi/gcp";
import * as pulumi from "@pulumi/pulumi";
import {Bucket, BucketIAMBinding} from "@pulumi/gcp/storage";

let gcpConfig = new pulumi.Config("gcp");

const staticSiteBucket = new Bucket("fe-static", {
    name: "fe-static",
    cors: [{
        maxAgeSeconds: 3600,
        methods: ["GET", "HEAD", "PUT", "POST", "DELETE",],
        origins: ["*"],
        responseHeaders: ["*"],
    }],
    forceDestroy: true,
    location: gcpConfig.require("region"),
    storageClass: "REGIONAL",
    uniformBucketLevelAccess: true,
    website: {
        mainPageSuffix: "index.html",
        notFoundPage: "404.html",
    },
});

const binding = new BucketIAMBinding("memberSource", {
    bucket: staticSiteBucket.name,
    members: ["allUsers"],
    role: "roles/storage.objectViewer",
});

export const siteBackend = new gcp.compute.BackendBucket("site-backend-bucket", {
    description: "",
    bucketName: staticSiteBucket.name,
    enableCdn: true,
});


