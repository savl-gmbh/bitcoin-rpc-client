package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.Unspent;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.math.BigDecimal;
import java.util.Map;

@SuppressWarnings("serial")
class UnspentWrapper extends MapWrapper implements Unspent {

    UnspentWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public String txid() {
        return mapStr("txid");
    }

    @Override
    public Integer vout() {
        return mapInt("vout");
    }

    @Override
    public String address() {
        return mapStr("address");
    }

    @Override
    public String scriptPubKey() {
        return mapStr("scriptPubKey");
    }

    @Deprecated
    @Override
    public String account() {
        return mapStr("account");
    }

    @Override
    public BigDecimal amount() {
        return mapBigDecimal("amount");
    }

    @Override
    public byte[] data() {
        return mapHex("data");
    }

    @Override
    public Integer confirmations() {
        return mapInt("confirmations");
    }

    @Override
    public String redeemScript() {
        return mapStr("redeemScript");
    }

    @Override
    public String witnessScript() {
        return mapStr("witnessScript");
    }

    @Override
    public String label() {
        return mapStr("label");
    }

    @Override
    public Boolean spendable() {
        return mapBool("spendable");
    }

    @Override
    public Boolean solvable() {
        return mapBool("solvable");
    }

    @Override
    public String desc() {
        return mapStr("desc");
    }

    @Override
    public Boolean safe() {
        return mapBool("safe");
    }
}
