package com.savl.bitcoin.rpc.client.api;

import com.savl.bitcoin.rpc.client.util.MapWrapperType;

import java.io.Serializable;
import java.math.BigDecimal;

public interface SmartFeeResult extends MapWrapperType, Serializable {

    int blocks();

    BigDecimal feeRate();

    String errors();
}
