package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.tx.RawTransactionSigningOrVerificationError;
import com.savl.bitcoin.rpc.client.api.tx.SignedRawTransaction;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
class SignedRawTransactionWrapper extends MapWrapper implements SignedRawTransaction, Serializable {

    SignedRawTransactionWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public String hex() {
        return mapStr("hex");
    }

    @Override
    public boolean complete() {
        return mapBool("complete");
    }

    @Override
    public List<RawTransactionSigningOrVerificationError> errors() {
        if (!m.containsKey("errors"))
            return null;

        @SuppressWarnings("unchecked")
        List<Map<String, ?>> list = (List<Map<String, ?>>) m.get("errors");

        return new RawTransactionSigningOrVerificationErrorList(list);
    }
}
