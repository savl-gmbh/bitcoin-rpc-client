package com.savl.bitcoin.rpc.client.api.address;

import com.savl.bitcoin.rpc.client.BitcoinJSONRPCClient;

/**
 * the result return by {@link BitcoinJSONRPCClient#getAddressUtxo(String)}
 *
 * @author frankchen
 */
public interface AddressUtxo {
    String getAddress();

    String getTxid();

    int getOutputIndex();

    String getScript();

    long getSatoshis();

    long getHeight();
}
