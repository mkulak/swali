import * as gcp from "@pulumi/gcp";
import {Config} from "@pulumi/pulumi";
import * as fs from "fs";
import {network} from "../network/nat";

let gcpConfig = new Config("gcp");
let config = new Config();

const container_declaration = fs.readFileSync(`${__dirname}/metadata-docker.yaml`, "utf-8")

const image = gcp.compute.getImage({
    project: "cos-cloud",
    family: "cos-85-lts",
    // project: "debian-cloud",
    // family: "debian-9",
});

const instanceTemplate = new gcp.compute.InstanceTemplate("template", {
    disks: [{
        autoDelete: true,
        boot: true,
        sourceImage: image.then(it => it.selfLink),
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
        "gce-container-declaration": container_declaration,
        // "shutdown-script": ""
    },
    // metadataStartupScript: startupScript,
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
    // labels: options.labels,
    // tags: options.tags,
});

const targetPool = new gcp.compute.TargetPool("client-pool", {
    region: gcpConfig.require("region")
});
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
        // {
        //     name: "nginx",
        //     port: 80,
        // }
    ],
    targetPools: [targetPool.id],
    // statefulDisks: {},
    targetSize: config.requireNumber("instance-count"),
    zone: gcpConfig.require("instance-group-zone")
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

export const groupBackend = new gcp.compute.BackendService("group-backend", {
    portName: "app",
    protocol: "HTTP",
    timeoutSec: 5,
    healthChecks: healthCheck.id,
    loadBalancingScheme: "EXTERNAL",
    sessionAffinity: "GENERATED_COOKIE",
    // localityLbPolicy: "RING_HASH",
    // sessionAffinity: "HTTP_COOKIE",
    // loadBalancingScheme: "INTERNAL_SELF_MANAGED",
    // consistentHash: {
    //     // httpCookie: {
    //     //     ttl: {
    //     //         seconds: 86400,
    //     //     },
    //     //     name: "app_balancing",
    //     // },
    //     httpHeaderName: "app_balancing"
    // },
    backends: [{
        group: instanceGroupManager.instanceGroup,
    }],
});
