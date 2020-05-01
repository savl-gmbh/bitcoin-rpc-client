package com.savl.bitcoin.rpc.client.api.address;

import com.savl.bitcoin.rpc.client.util.MapWrapperType;

import java.io.Serializable;
import java.util.List;

public interface AddressInfo extends MapWrapperType, Serializable {
    String address();

    String scriptPubKey();

    Boolean isMine();

    Boolean isWatchOnly();

    Boolean solvable();

    String desc();

    Boolean isScript();

    Boolean isChange();

    Boolean isWitness();

    Integer witnessVersion();

    String witnessProgram();

    String script();

    String hex();

    List<String> pubKeys();

    Integer sigsRequired();

    String pubKey();

    AddressInfo embedded();

    Boolean isCompressed();

    String label();

    Long timestamp();

    String hdKeyPath();

    String hdSeedId();

    String hdMasterFingerprint();

    List<AddressInfoLabel> labels();
}
