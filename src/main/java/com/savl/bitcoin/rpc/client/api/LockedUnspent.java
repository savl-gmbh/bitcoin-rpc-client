package com.savl.bitcoin.rpc.client.api;

import com.savl.bitcoin.rpc.client.util.MapWrapperType;

import java.io.Serializable;

public interface LockedUnspent extends MapWrapperType, Serializable {

    String txId();

    int vout();
}
