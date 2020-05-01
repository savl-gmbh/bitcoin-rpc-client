package com.savl.bitcoin.rpc.client.api.tx;

import com.savl.bitcoin.rpc.client.util.MapWrapperType;

import java.io.Serializable;
import java.math.BigDecimal;

public interface TxOutSetInfo extends MapWrapperType, Serializable {

    long height();

    String bestBlock();

    long transactions();

    long txouts();

    long bytesSerialized();

    String hashSerialized();

    BigDecimal totalAmount();
}
