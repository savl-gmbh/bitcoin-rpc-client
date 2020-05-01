package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.DecodedScript;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
class DecodedScriptWrapper extends MapWrapper implements DecodedScript, Serializable {

    DecodedScriptWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public String asm() {
        return mapStr("asm");
    }

    @Override
    public String hex() {
        return mapStr("hex");
    }

    @Override
    public String type() {
        return mapStr("type");
    }

    @Override
    public int reqSigs() {
        return mapInt("reqSigs");
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> addresses() {
        return (List<String>) m.get("addresses");
    }

    @Override
    public String p2sh() {
        return mapStr("p2sh");
    }
}
