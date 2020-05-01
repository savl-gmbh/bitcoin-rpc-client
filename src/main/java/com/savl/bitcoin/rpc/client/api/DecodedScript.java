package com.savl.bitcoin.rpc.client.api;

import com.savl.bitcoin.rpc.client.util.MapWrapperType;

import java.io.Serializable;
import java.util.List;

public interface DecodedScript extends MapWrapperType, Serializable {

    String asm();

    String hex();

    String type();

    int reqSigs();

    List<String> addresses();

    String p2sh();
}
