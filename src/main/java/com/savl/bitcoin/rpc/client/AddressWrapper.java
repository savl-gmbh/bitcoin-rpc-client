package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.address.Address;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
class AddressWrapper extends MapWrapper implements Address, Serializable {

    AddressWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public String address() {
        return mapStr("address");
    }

    @Override
    public String connected() {
        return mapStr("connected");
    }
}
