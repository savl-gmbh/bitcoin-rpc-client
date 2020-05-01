package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.Unspent;
import com.savl.bitcoin.rpc.client.util.ListMapWrapper;

import java.util.List;
import java.util.Map;

class UnspentListWrapper extends ListMapWrapper<Unspent> {

    UnspentListWrapper(List<Map<String, ?>> list) {
        super(list);
    }

    @Override
    protected Unspent wrap(Map<String, ?> m) {
        return new UnspentWrapper(m);
    }
}
