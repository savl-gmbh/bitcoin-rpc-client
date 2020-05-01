package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.address.AddressUtxo;

import java.util.Map;

class AddressUtxoWrapper implements AddressUtxo {
    private final String address;
    private final String txid;
    private final int outputIndex;
    private final String script;
    private final long satoshis;
    private final long height;

    AddressUtxoWrapper(Map<String, ?> result) {
        address = getOrDefault(result, "address", "");
        txid = getOrDefault(result, "txid", "");
        outputIndex = getOrDefault(result, "outputIndex", 0);
        script = getOrDefault(result, "script", "");
        satoshis = getOrDefault(result, "satoshis", 0L);
        height = getOrDefault(result, "height", -1L);
    }

    @SuppressWarnings("unchecked")
    private <T extends Object> T getOrDefault(Map<String, ?> result, String key, T defval) {
        T val = (T) result.get(key);
        return val != null ? val : defval;
    }

    public String getAddress() {
        return address;
    }

    public String getTxid() {
        return txid;
    }

    public int getOutputIndex() {
        return outputIndex;
    }

    public String getScript() {
        return script;
    }

    public long getSatoshis() {
        return satoshis;
    }

    public long getHeight() {
        return height;
    }
}
