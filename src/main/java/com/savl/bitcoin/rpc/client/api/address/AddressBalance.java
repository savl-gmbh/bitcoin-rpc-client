package com.savl.bitcoin.rpc.client.api.address;

import com.savl.bitcoin.rpc.client.BitcoinJSONRPCClient;

/**
 * the result returned by
 * {@link BitcoinJSONRPCClient#getAddressBalance(String)}
 *
 * @author frankchen
 */
public interface AddressBalance {
    long getBalance();

    long getReceived();
}
