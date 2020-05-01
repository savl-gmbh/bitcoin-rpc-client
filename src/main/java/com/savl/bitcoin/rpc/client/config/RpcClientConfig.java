package com.savl.bitcoin.rpc.client.config;

import org.aeonbits.owner.ConfigCache;

public class RpcClientConfig {
    public static RpcClientConfigI get() {
        return ConfigCache.getOrCreate(RpcClientConfigI.class);
    }
}
