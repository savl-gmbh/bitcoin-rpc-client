package com.savl.bitcoin.rpc.client.api.tx;

import com.savl.bitcoin.rpc.client.util.MapWrapperType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public interface Transaction extends MapWrapperType, Serializable {

    String account();

    String address();

    String category();

    BigDecimal amount();

    BigDecimal fee();

    int confirmations();

    String blockHash();

    int blockIndex();

    Date blockTime();

    String txId();

    Date time();

    Date timeReceived();

    String comment();

    String commentTo();

    boolean generated();

    RawTransaction raw();
}
