package com.savl.bitcoin.rpc.client.util;

import com.savl.bitcoin.rpc.client.api.BitcoindRpcClient;
import com.savl.bitcoin.rpc.client.api.BlockChainInfo;

public class ChainVerifier {
    /**
     * Verify what running on proper blockhain mode
     *
     * @param chain {@link Chain}
     * @param client Instance of {@link com.savl.bitcoin.rpc.client.BitcoinJSONRPCClient}
     * @throws Exception in case of unexpected chain running
     */
    public static void ensureRunningOnChain(Chain chain, BitcoindRpcClient client) throws Exception {
        BlockChainInfo blockChainInfo = client.getBlockChainInfo();

        String expectedChain = chain.toString().toLowerCase();
        String actualChain = blockChainInfo.chain();

        if (!actualChain.equals(expectedChain))
            throw new Exception("Expected to run on the " + expectedChain + " blockchain, "
                    + "but client is configured to use: " + actualChain);
    }

    /**
     * Represents the possible values of the chain we're running on.
     * Defined by the property "chain" of https://bitcoin.org/en/developer-reference#getblockchaininfo
     */
    public enum Chain {
        MAIN,
        TEST,
        REGTEST
    }
}
