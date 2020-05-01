package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.address.AddressInfoLabel;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.io.Serializable;
import java.util.Map;

class AddressInfoLabelWrapper extends MapWrapper implements AddressInfoLabel, Serializable {
    private static final long serialVersionUID = 3290420293956206271L;

    AddressInfoLabelWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public String name() {
        return mapStr("name");
    }

    @Override
    public String purpose() {
        return mapStr("purpose");
    }
}
