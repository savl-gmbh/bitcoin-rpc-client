package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.address.AddressBalance;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
class AddressBalanceWrapper extends MapWrapper implements AddressBalance, Serializable {
    AddressBalanceWrapper(Map<String, ?> r) {
        super(r);
    }

    @Override
    public long getBalance() {
        return mapLong("balance");
    }

    public long getReceived() {
        return mapLong("received");
    }
}
