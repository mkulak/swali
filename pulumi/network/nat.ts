import {Firewall, Network, Router, RouterNat} from "@pulumi/gcp/compute";

export const network = new Network("network")

const firewall = new Firewall("firewall", {
    network: network.id,
    direction: "INGRESS",
    allows: [
        {protocol: "tcp", ports: ["22", "80", "443", "8080"],},
    ]
})

const router = new Router("router", {
    name: "router",
    network: network.id,
})

const nat = new RouterNat("nat", {
    name: "nat",
    router: router.name,
    sourceSubnetworkIpRangesToNat: "ALL_SUBNETWORKS_ALL_IP_RANGES",
    natIpAllocateOption: "AUTO_ONLY",
})

