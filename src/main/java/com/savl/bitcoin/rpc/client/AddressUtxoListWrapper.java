package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.address.AddressUtxo;
import com.savl.bitcoin.rpc.client.util.ListMapWrapper;

import java.util.List;
import java.util.Map;

class AddressUtxoListWrapper extends ListMapWrapper<AddressUtxo> {

    AddressUtxoListWrapper(List<Map<String, ?>> list) {
        super(list);
    }

    @Override
    protected AddressUtxo wrap(Map<String, ?> m) {
        return new AddressUtxoWrapper(m);
    }
}
