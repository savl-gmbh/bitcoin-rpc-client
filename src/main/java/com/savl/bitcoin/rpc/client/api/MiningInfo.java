package com.savl.bitcoin.rpc.client.api;

import com.savl.bitcoin.rpc.client.util.MapWrapperType;

import java.io.Serializable;
import java.math.BigDecimal;

public interface MiningInfo extends MapWrapperType, Serializable {

    int blocks();

    int currentBlockSize();

    int currentBlockWeight();

    int currentBlockTx();

    BigDecimal difficulty();

    String errors();

    BigDecimal networkHashps();

    int pooledTx();

    boolean testNet();

    String chain();
}
