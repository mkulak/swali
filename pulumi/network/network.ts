import {
    Firewall,
    GlobalAddress,
    ManagedSslCertificate,
    Network,
    Router,
    RouterNat,
    TargetHttpsProxy,
    URLMap
} from "@pulumi/gcp/compute";
import * as gcp from "@pulumi/gcp";
import {Config} from "@pulumi/pulumi";
import {siteBackend} from "../static-site/site";
import {groupBackend} from "../instance-group/instance";

let config = new Config();

// export const ipAddress = new GlobalAddress("site-lb-ip", {
//     name: "site-lb-ip",
//     addressType: 'EXTERNAL',
// })

export const ipAddress = gcp.compute.getGlobalAddress({ name: "site-lb-ip" })

const httpUrlMap = new URLMap("http-map", {
    name: 'http-map',
    defaultUrlRedirect: {
        httpsRedirect: true,
        stripQuery: false,
    }
});

const sslCertificate = new ManagedSslCertificate("swali-certificate", {
    name: "swali-certificate",
    managed: {domains: [config.require("swali-host")],},
    type: "MANAGED",
});

const appSslCertificate = new ManagedSslCertificate("app-certificate", {
    name: "app-certificate",
    managed: {domains: [config.require("app-host")],},
    type: "MANAGED",
});


const httpProxy = new gcp.compute.TargetHttpProxy("http-proxy", {
    name: "http-proxy",
    urlMap: httpUrlMap.id,
});


const httpForwardingRule = new gcp.compute.GlobalForwardingRule("http-to-https", {
    name: 'http-to-https',
    portRange: '80',
    ipAddress: ipAddress.then(it => it.address),
    // ipAddress: ipAddress.address,
    target: httpProxy.id,
});


const urlMap = new URLMap("site-lb", {
    description: "",
    name: 'site-lb',
    defaultService: siteBackend.id,
    hostRules: [
        {
            hosts: [config.require("swali-host")],
            pathMatcher: "swali",
        },
        {
            hosts: [config.require("app-host")],
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
            defaultService: groupBackend.id,
            defaultUrlRedirect: undefined,
            // defaultUrlRedirect: {
            //     hostRedirect: "google.com",
            //     stripQuery: true
            // },
        },
    ],
});


const proxy = new TargetHttpsProxy("site-lb-target-proxy", {
    name: "site-lb-target-proxy",
    urlMap: urlMap.id,
    sslCertificates: [sslCertificate.id, appSslCertificate.id],
});

const forwardingRule = new gcp.compute.GlobalForwardingRule("site-fe", {
    name: 'site-fe',
    portRange: '443-443',
    ipAddress: ipAddress.then(it => it.address),
    // ipAddress: ipAddress.address,
    target: proxy.id,
});

