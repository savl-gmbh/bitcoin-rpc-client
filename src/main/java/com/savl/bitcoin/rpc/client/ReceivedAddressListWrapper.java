package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.address.ReceivedAddress;

import java.util.AbstractList;
import java.util.List;
import java.util.Map;

class ReceivedAddressListWrapper extends AbstractList<ReceivedAddress> {

    private final List<Map<String, ?>> wrappedList;

    ReceivedAddressListWrapper(List<Map<String, ?>> wrappedList) {
        this.wrappedList = wrappedList;
    }

    @Override
    public ReceivedAddress get(int index) {
        final Map<String, ?> m = wrappedList.get(index);
        return new ReceivedAddressWrapper(m);
    }

    @Override
    public int size() {
        return wrappedList.size();
    }
}
