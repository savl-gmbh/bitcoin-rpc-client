package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.MultiSig;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
class MultiSigWrapper extends MapWrapper implements MultiSig, Serializable {

    MultiSigWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public String address() {
        return mapStr("address");
    }

    @Override
    public String redeemScript() {
        return mapStr("redeemScript");
    }
}
