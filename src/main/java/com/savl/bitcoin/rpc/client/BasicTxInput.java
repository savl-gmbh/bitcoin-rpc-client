package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.tx.TxInput;

import java.math.BigDecimal;

@SuppressWarnings("serial")
public class BasicTxInput implements TxInput {

    public String txid;
    public Integer vout;
    public String scriptPubKey;
    public BigDecimal amount;

    public BasicTxInput(String txid, Integer vout) {
        this.txid = txid;
        this.vout = vout;
    }

    public BasicTxInput(String txid, Integer vout, String scriptPubKey) {
        this(txid, vout);
        this.scriptPubKey = scriptPubKey;
    }

    public BasicTxInput(String txid, Integer vout, String scriptPubKey, BigDecimal amount) {
        this(txid, vout, scriptPubKey);
        this.amount = amount;
    }

    @Override
    public String txid() {
        return txid;
    }

    @Override
    public Integer vout() {
        return vout;
    }

    @Override
    public String scriptPubKey() {
        return scriptPubKey;
    }

    @Override
    public BigDecimal amount() {
        return amount;
    }
}
