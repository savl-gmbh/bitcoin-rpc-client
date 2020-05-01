package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.tx.Transaction;
import com.savl.bitcoin.rpc.client.api.tx.TransactionsSinceBlock;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
class TransactionsSinceBlockWrapper implements TransactionsSinceBlock, Serializable {

    private final List<Transaction> transactions;
    private final String lastBlock;

    @SuppressWarnings("unchecked")
    TransactionsSinceBlockWrapper(BitcoinJSONRPCClient client, Map<String, ?> r) {
        this.transactions = new TransactionListMapWrapper(client, (List<Map<String, ?>>) r.get("transactions"));
        this.lastBlock = (String) r.get("lastblock");
    }

    @Override
    public List<Transaction> transactions() {
        return transactions;
    }

    @Override
    public String lastBlock() {
        return lastBlock;
    }
}
