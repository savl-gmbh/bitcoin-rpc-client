package com.savl.bitcoin.rpc.client.api.tx;

import java.math.BigDecimal;

public interface UnspentTxOutput extends TxInput {

    public String txid();

    public Integer vout();

    public String scriptPubKey();

    public String desc();

    public BigDecimal amount();

    public int height();
}
