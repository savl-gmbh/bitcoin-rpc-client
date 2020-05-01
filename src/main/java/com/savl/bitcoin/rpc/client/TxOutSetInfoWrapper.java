package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.tx.TxOutSetInfo;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@SuppressWarnings("serial")
class TxOutSetInfoWrapper extends MapWrapper implements TxOutSetInfo, Serializable {

    TxOutSetInfoWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public long height() {
        return mapInt("height");
    }

    @Override
    public String bestBlock() {
        return mapStr("bestBlock");
    }

    @Override
    public long transactions() {
        return mapInt("transactions");
    }

    @Override
    public long txouts() {
        return mapInt("txouts");
    }

    @Override
    public long bytesSerialized() {
        return mapInt("bytes_serialized");
    }

    @Override
    public String hashSerialized() {
        return mapStr("hash_serialized");
    }

    @Override
    public BigDecimal totalAmount() {
        return mapBigDecimal("total_amount");
    }
}
