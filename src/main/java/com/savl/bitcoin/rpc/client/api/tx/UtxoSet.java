package com.savl.bitcoin.rpc.client.api.tx;

import java.math.BigDecimal;
import java.util.List;

public interface UtxoSet {
    public Integer searchedItems();

    public BigDecimal totalAmount();

    public List<UnspentTxOutput> unspents();
}
