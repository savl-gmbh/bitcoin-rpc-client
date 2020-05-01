package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.tx.RawTransactionSigningOrVerificationError;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
class RawTransactionSigningOrVerificationErrorWrapper extends MapWrapper implements RawTransactionSigningOrVerificationError, Serializable {
    RawTransactionSigningOrVerificationErrorWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public String txId() {
        return mapStr("txid");
    }

    @Override
    public int vOut() {
        return mapInt("vout");
    }

    @Override
    public String scriptSig() {
        return mapStr("scriptSig");
    }

    @Override
    public int n() {
        return mapInt("sequence");
    }

    @Override
    public String error() {
        return mapStr("error");
    }
}
