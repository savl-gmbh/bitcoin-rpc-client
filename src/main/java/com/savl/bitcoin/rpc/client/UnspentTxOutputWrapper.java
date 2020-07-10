package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.tx.UnspentTxOutput;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@SuppressWarnings("serial")
public class UnspentTxOutputWrapper extends MapWrapper implements UnspentTxOutput, Serializable {

    public UnspentTxOutputWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public String txid() {
        return mapStr("txid");
    }

    @Override
    public Integer vout() {
        return mapInt("vout");
    }

    @Override
    public String scriptPubKey() {
        return mapStr("scriptPubKey");
    }

    @Override
    public BigDecimal amount() {
        return mapBigDecimal("amount");
    }

    @Override
    public int height() {
        return mapInt("height");
    }

    @Override
    public String desc() {
        return mapStr("desc");
    }

}
