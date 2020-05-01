package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.address.AddressInfo;
import com.savl.bitcoin.rpc.client.api.address.AddressInfoLabel;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

class AddressInfoMapWrapper extends MapWrapper implements AddressInfo, Serializable {
    private static final long serialVersionUID = 8801943420993238518L;

    AddressInfoMapWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public String address() {
        return mapStr("address");
    }

    @Override
    public String scriptPubKey() {
        return mapStr("scriptPubKey");
    }

    @Override
    public Boolean isMine() {
        return mapBool("ismine");
    }

    @Override
    public Boolean isWatchOnly() {
        return mapBool("iswatchonly");
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
    public Boolean isScript() {
        return mapBool("isscript");
    }

    @Override
    public Boolean isChange() {
        return mapBool("ischange");
    }

    @Override
    public Boolean isWitness() {
        return mapBool("iswitness");
    }

    @Override
    public Integer witnessVersion() {
        return mapInt("witness_version");
    }

    @Override
    public String witnessProgram() {
        return mapStr("witness_program");
    }

    @Override
    public String script() {
        return mapStr("script");
    }

    @Override
    public String hex() {
        return mapStr("hex");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> pubKeys() {
        if (!m.containsKey("pubkeys"))
            return null;

        return (List<String>) m.get("pubkeys");
    }

    @Override
    public Integer sigsRequired() {
        return mapInt("sigsrequired");
    }

    @Override
    public String pubKey() {
        return mapStr("pubkey");
    }

    @SuppressWarnings("unchecked")
    @Override
    public AddressInfo embedded() {
        if (!m.containsKey("embedded"))
            return null;

        return new AddressInfoMapWrapper((Map<String, ?>) m.get("embedded"));
    }

    @Override
    public Boolean isCompressed() {
        return mapBool("iscompressed");
    }

    @Override
    public String label() {
        return mapStr("label");
    }

    @Override
    public Long timestamp() {
        return mapLong("timestamp");
    }

    @Override
    public String hdKeyPath() {
        return mapStr("hdkeypath");
    }

    @Override
    public String hdSeedId() {
        return mapStr("hdseedid");
    }

    @Override
    public String hdMasterFingerprint() {
        return mapStr("hdmasterfingerprint");
    }

    @Override
    public List<AddressInfoLabel> labels() {
        if (!m.containsKey("labels"))
            return null;

        @SuppressWarnings("unchecked")
        List<Map<String, ?>> list = (List<Map<String, ?>>) m.get("labels");

        return new AddressInfoLabelListWrapper(list);
    }
}
