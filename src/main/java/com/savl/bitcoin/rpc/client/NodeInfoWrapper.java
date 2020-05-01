package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.NodeInfo;
import com.savl.bitcoin.rpc.client.api.address.Address;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
class NodeInfoWrapper extends MapWrapper implements NodeInfo, Serializable {

    NodeInfoWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public String addedNode() {
        return mapStr("addednode");
    }

    @Override
    public boolean connected() {
        return mapBool("connected");
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Address> addresses() {
        List<Map<String, ?>> maps = (List<Map<String, ?>>) m.get("addresses");
        List<Address> addresses = new LinkedList<Address>();
        for (Map<String, ?> m : maps) {
            Address add = new AddressWrapper(m);
            addresses.add(add);
        }
        return addresses;
    }
}
