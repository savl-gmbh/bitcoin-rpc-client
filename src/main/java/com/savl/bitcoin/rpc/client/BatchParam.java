package com.savl.bitcoin.rpc.client;

class BatchParam {
    public final String id;
    public final Object[] params;

    BatchParam(String id, Object[] params) {
        this.id = id;
        this.params = params;
    }
}
