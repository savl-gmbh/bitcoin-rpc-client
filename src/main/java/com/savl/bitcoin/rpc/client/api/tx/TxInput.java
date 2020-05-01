package com.savl.bitcoin.rpc.client.api.tx;

import java.io.Serializable;
import java.math.BigDecimal;

public interface TxInput extends Serializable {

    String txid();

    Integer vout();

    String scriptPubKey();

    BigDecimal amount();
}
