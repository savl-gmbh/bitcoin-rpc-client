package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.SmartFeeResult;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@SuppressWarnings("serial")
class SmartFeeResultMapWrapper extends MapWrapper implements SmartFeeResult, Serializable {

    SmartFeeResultMapWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public BigDecimal feeRate() {
        return mapBigDecimal("feerate");
    }

    @Override
    public int blocks() {
        return mapInt("blocks");
    }

    @Override
    public String errors() {
        return mapStr("errors");
    }
}
