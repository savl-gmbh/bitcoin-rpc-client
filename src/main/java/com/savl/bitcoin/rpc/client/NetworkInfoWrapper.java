package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.Network;
import com.savl.bitcoin.rpc.client.api.NetworkInfo;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
class NetworkInfoWrapper extends MapWrapper implements NetworkInfo, Serializable {

    NetworkInfoWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public long version() {
        return mapLong("version");
    }

    @Override
    public String subversion() {
        return mapStr("subversion");
    }

    @Override
    public long protocolVersion() {
        return mapLong("protocolversion");
    }

    @Override
    public String localServices() {
        return mapStr("localservices");
    }

    @Override
    public boolean localRelay() {
        return mapBool("localrelay");
    }

    @Override
    public long timeOffset() {
        return mapLong("timeoffset");
    }

    @Override
    public long connections() {
        return mapLong("connections");
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Network> networks() {
        List<Map<String, ?>> maps = (List<Map<String, ?>>) m.get("networks");
        List<Network> networks = new LinkedList<Network>();
        for (Map<String, ?> m : maps) {
            Network net = new NetworkWrapper(m);
            networks.add(net);
        }
        return networks;
    }

    @Override
    public BigDecimal relayFee() {
        return mapBigDecimal("relayfee");
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> localAddresses() {
        return (List<String>) m.get("localaddresses");
    }

    @Override
    public String warnings() {
        return mapStr("warnings");
    }
}
