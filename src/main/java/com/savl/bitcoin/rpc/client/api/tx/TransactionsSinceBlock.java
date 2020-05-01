package com.savl.bitcoin.rpc.client.api.tx;

import java.io.Serializable;
import java.util.List;

public interface TransactionsSinceBlock extends Serializable {

    List<Transaction> transactions();

    String lastBlock();
}
