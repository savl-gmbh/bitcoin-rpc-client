package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.tx.TxOut;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
class TxOutWrapper extends MapWrapper implements TxOut, Serializable {

    TxOutWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public String bestBlock() {
        return mapStr("bestblock");
    }

    @Override
    public long confirmations() {
        return mapLong("confirmations");
    }

    @Override
    public BigDecimal value() {
        return mapBigDecimal("value");
    }

    @Override
    public String asm() {
        return mapStr("asm");
    }

    @Override
    public String hex() {
        return mapStr("hex");
    }

    @Override
    public long reqSigs() {
        return mapLong("reqSigs");
    }

    @Override
    public String type() {
        return mapStr("type");
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> addresses() {
        return (List<String>) m.get("addresses");
    }

    @Override
    public long version() {
        return mapLong("version");
    }

    @Override
    public boolean coinBase() {
        return mapBool("coinbase");
    }
}
