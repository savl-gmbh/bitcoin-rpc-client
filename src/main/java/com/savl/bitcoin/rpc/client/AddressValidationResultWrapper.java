package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.address.AddressValidationResult;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.util.Map;

@SuppressWarnings("serial")
class AddressValidationResultWrapper extends MapWrapper implements AddressValidationResult {

    AddressValidationResultWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public boolean isValid() {
        return mapBool("isvalid");
    }

    @Override
    public String address() {
        return mapStr("address");
    }

    @Override
    public boolean isMine() {
        return mapBool("ismine");
    }

    @Override
    public boolean isScript() {
        return mapBool("isscript");
    }

    @Override
    public String pubKey() {
        return mapStr("pubkey");
    }

    @Override
    public boolean isCompressed() {
        return mapBool("iscompressed");
    }

    @Override
    public String account() {
        return mapStr("account");
    }
}
