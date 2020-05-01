package com.savl.bitcoin.rpc.client.api;

import com.savl.bitcoin.rpc.client.util.MapWrapperType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface NetworkInfo extends MapWrapperType, Serializable {

    long version();

    String subversion();

    long protocolVersion();

    String localServices();

    boolean localRelay();

    long timeOffset();

    long connections();

    List<Network> networks();

    BigDecimal relayFee();

    List<String> localAddresses();

    String warnings();
}
