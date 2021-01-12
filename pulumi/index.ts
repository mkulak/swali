import * as site from "./static-site/site";
import * as network from "./network/network";
import * as instance from "./instance-group/instance";
import {groupBackend} from "./instance-group/instance";

export const backendId = site.siteBackend.id;
export const ip = network.ipAddress.then(it => it.address);
// export const ip = network.ipAddress.address;
export const groupBackendId = groupBackend.id;
