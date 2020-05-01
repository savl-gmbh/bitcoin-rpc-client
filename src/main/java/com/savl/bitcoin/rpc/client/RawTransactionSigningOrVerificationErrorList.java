package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.tx.RawTransactionSigningOrVerificationError;
import com.savl.bitcoin.rpc.client.util.ListMapWrapper;

import java.util.List;
import java.util.Map;

class RawTransactionSigningOrVerificationErrorList
        extends ListMapWrapper<RawTransactionSigningOrVerificationError> {

    RawTransactionSigningOrVerificationErrorList(List<Map<String, ?>> list) {
        super(list);
    }

    @Override
    protected RawTransactionSigningOrVerificationError wrap(Map<String, ?> m) {
        return new RawTransactionSigningOrVerificationErrorWrapper(m);
    }
}
