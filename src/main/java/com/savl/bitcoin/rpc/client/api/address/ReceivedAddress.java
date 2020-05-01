package com.savl.bitcoin.rpc.client.api.address;

import com.savl.bitcoin.rpc.client.util.MapWrapperType;

import java.io.Serializable;
import java.math.BigDecimal;

public interface ReceivedAddress extends MapWrapperType, Serializable {

    String address();

    String account();

    BigDecimal amount();

    int confirmations();
}
