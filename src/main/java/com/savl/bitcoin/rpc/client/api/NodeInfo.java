package com.savl.bitcoin.rpc.client.api;

import com.savl.bitcoin.rpc.client.api.address.Address;
import com.savl.bitcoin.rpc.client.util.MapWrapperType;

import java.io.Serializable;
import java.util.List;

public interface NodeInfo extends MapWrapperType, Serializable {

    String addedNode();

    boolean connected();

    List<Address> addresses();

}
