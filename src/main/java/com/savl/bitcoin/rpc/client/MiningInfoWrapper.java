package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.MiningInfo;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@SuppressWarnings("serial")
class MiningInfoWrapper extends MapWrapper implements MiningInfo, Serializable {

    MiningInfoWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public int blocks() {
        return mapInt("blocks");
    }

    @Override
    public int currentBlockSize() {
        return mapInt("currentblocksize");
    }

    @Override
    public int currentBlockWeight() {
        return mapInt("currentblockweight");
    }

    @Override
    public int currentBlockTx() {
        return mapInt("currentblocktx");
    }

    @Override
    public BigDecimal difficulty() {
        return mapBigDecimal("difficulty");
    }

    @Override
    public String errors() {
        return mapStr("errors");
    }

    @Override
    public BigDecimal networkHashps() {
        return mapBigDecimal("networkhashps");
    }

    @Override
    public int pooledTx() {
        return mapInt("pooledtx");
    }

    @Override
    public boolean testNet() {
        return mapBool("testnet");
    }

    @Override
    public String chain() {
        return mapStr("chain");
    }
}
