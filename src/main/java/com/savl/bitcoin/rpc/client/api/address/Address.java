package com.savl.bitcoin.rpc.client.api.address;

import com.savl.bitcoin.rpc.client.util.MapWrapperType;

import java.io.Serializable;

public interface Address extends MapWrapperType, Serializable {

    String address();

    String connected();
}
