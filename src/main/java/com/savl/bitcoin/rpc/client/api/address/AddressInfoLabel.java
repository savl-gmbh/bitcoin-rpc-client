package com.savl.bitcoin.rpc.client.api.address;

import com.savl.bitcoin.rpc.client.util.MapWrapperType;

import java.io.Serializable;

public interface AddressInfoLabel extends MapWrapperType, Serializable {
    String name();

    String purpose();
}
