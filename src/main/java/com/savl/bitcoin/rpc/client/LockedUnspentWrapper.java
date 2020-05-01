package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.LockedUnspent;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.util.Map;

@SuppressWarnings("serial")
class LockedUnspentWrapper extends MapWrapper implements LockedUnspent {

    LockedUnspentWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public String txId() {
        return (String) m.get("txid");
    }

    @Override
    public int vout() {
        return ((Long) m.get("vout")).intValue();
    }
}
