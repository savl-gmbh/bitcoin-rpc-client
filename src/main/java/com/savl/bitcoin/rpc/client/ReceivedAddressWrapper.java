package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.address.ReceivedAddress;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.math.BigDecimal;
import java.util.Map;

@SuppressWarnings("serial")
class ReceivedAddressWrapper extends MapWrapper implements ReceivedAddress {

    ReceivedAddressWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public String address() {
        return mapStr("address");
    }

    @Override
    public String account() {
        return mapStr("account");
    }

    @Override
    public BigDecimal amount() {
        return mapBigDecimal("amount");
    }

    @Override
    public int confirmations() {
        return mapInt("confirmations");
    }
}
