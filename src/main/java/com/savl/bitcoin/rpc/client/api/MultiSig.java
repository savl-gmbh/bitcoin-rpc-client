package com.savl.bitcoin.rpc.client.api;

import com.savl.bitcoin.rpc.client.util.MapWrapperType;

import java.io.Serializable;

public interface MultiSig extends MapWrapperType, Serializable {

    String address();

    String redeemScript();
}
