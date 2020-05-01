package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.address.AddressInfoLabel;
import com.savl.bitcoin.rpc.client.util.ListMapWrapper;

import java.util.List;
import java.util.Map;

class AddressInfoLabelListWrapper extends ListMapWrapper<AddressInfoLabel> {

    AddressInfoLabelListWrapper(List<Map<String, ?>> list) {
        super(list);
    }

    @Override
    protected AddressInfoLabel wrap(Map<String, ?> m) {
        return new AddressInfoLabelWrapper(m);
    }
}
