package com.savl.bitcoin.rpc.client.api;

import com.savl.bitcoin.rpc.client.util.MapWrapperType;

import java.io.Serializable;

public interface Network extends MapWrapperType, Serializable {

    String name();

    boolean limited();

    boolean reachable();

    String proxy();

    boolean proxyRandomizeCredentials();
}
