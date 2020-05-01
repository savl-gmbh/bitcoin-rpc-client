package com.savl.bitcoin.rpc.client.api.address;

import com.savl.bitcoin.rpc.client.util.MapWrapperType;

import java.io.Serializable;

public interface AddressValidationResult extends MapWrapperType, Serializable {

    boolean isValid();

    String address();

    boolean isMine();

    boolean isScript();

    String pubKey();

    boolean isCompressed();

    String account();
}
