package com.savl.bitcoin.rpc.client.api;

import com.savl.bitcoin.rpc.client.util.MapWrapperType;

import java.io.Serializable;
import java.math.BigDecimal;

public interface BlockChainInfo extends MapWrapperType, Serializable {

    String chain();

    Integer blocks();

    Integer headers();

    String bestBlockHash();

    BigDecimal difficulty();

    Long medianTime();

    BigDecimal verificationProgress();

    Boolean initialBlockDownload();

    String chainWork();

    Long sizeOnDisk();

    Boolean pruned();

    Integer pruneHeight();

    Boolean automaticPruning();

    Long pruneTargetSize();

    String warnings();
}
