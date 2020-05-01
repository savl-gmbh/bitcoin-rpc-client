package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.Network;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
class NetworkWrapper extends MapWrapper implements Network, Serializable {

    NetworkWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public String name() {
        return mapStr("name");
    }

    @Override
    public boolean limited() {
        return mapBool("limited");
    }

    @Override
    public boolean reachable() {
        return mapBool("reachable");
    }

    @Override
    public String proxy() {
        return mapStr("proxy");
    }

    @Override
    public boolean proxyRandomizeCredentials() {
        return mapBool("proxy_randomize_credentials");
    }
}
