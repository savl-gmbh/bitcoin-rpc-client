package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.BlockChainInfo;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@SuppressWarnings("serial")
class BlockChainInfoMapWrapper extends MapWrapper implements BlockChainInfo, Serializable {

    BlockChainInfoMapWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public String chain() {
        return mapStr("chain");
    }

    @Override
    public Integer blocks() {
        return mapInt("blocks");
    }

    @Override
    public String bestBlockHash() {
        return mapStr("bestblockhash");
    }

    @Override
    public BigDecimal difficulty() {
        return mapBigDecimal("difficulty");
    }

    @Override
    public BigDecimal verificationProgress() {
        return mapBigDecimal("verificationprogress");
    }

    @Override
    public String chainWork() {
        return mapStr("chainwork");
    }

    @Override
    public Integer headers() {
        return mapInt("headers");
    }

    @Override
    public Long medianTime() {
        return mapLong("mediantime");
    }

    @Override
    public Boolean initialBlockDownload() {
        return mapBool("initialblockdownload");
    }

    @Override
    public Long sizeOnDisk() {
        return mapLong("size_on_disk");
    }

    @Override
    public Boolean pruned() {
        return mapBool("pruned");
    }

    @Override
    public Integer pruneHeight() {
        return mapInt("pruneheight");
    }

    @Override
    public Boolean automaticPruning() {
        return mapBool("automatic_pruning");
    }

    @Override
    public Long pruneTargetSize() {
        return mapLong("prune_target_size");
    }

    @Override
    public String warnings() {
        return mapStr("warnings");
    }
}
