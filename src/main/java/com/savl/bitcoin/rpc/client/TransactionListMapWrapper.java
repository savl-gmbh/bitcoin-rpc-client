package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.tx.Transaction;
import com.savl.bitcoin.rpc.client.util.ListMapWrapper;

import java.util.List;
import java.util.Map;

class TransactionListMapWrapper extends ListMapWrapper<Transaction> {

    private final BitcoinJSONRPCClient client;

    TransactionListMapWrapper(BitcoinJSONRPCClient client, List<Map<String, ?>> list) {
        super(list);
        this.client = client;
    }

    @Override
    protected Transaction wrap(Map<String, ?> m) {
        return new TransactionWrapper(client, m);
    }
}
