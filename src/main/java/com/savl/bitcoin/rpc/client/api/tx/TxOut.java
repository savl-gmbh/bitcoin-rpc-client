package com.savl.bitcoin.rpc.client.api.tx;

import com.savl.bitcoin.rpc.client.util.MapWrapperType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface TxOut extends MapWrapperType, Serializable {

    String bestBlock();

    long confirmations();

    BigDecimal value();

    String asm();

    String hex();

    long reqSigs();

    String type();

    List<String> addresses();

    long version();

    boolean coinBase();

}
