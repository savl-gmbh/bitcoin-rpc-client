package com.savl.bitcoin.rpc.client.integration;

import com.savl.bitcoin.rpc.client.BitcoinJSONRPCClient;
import com.savl.bitcoin.rpc.client.api.BitcoindRpcClient;
import com.savl.bitcoin.rpc.client.util.ChainVerifier;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;

import java.util.logging.Logger;

import static com.savl.bitcoin.rpc.client.util.ChainVerifier.Chain;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Common framework for integration tests
 *
 * In order to run these tests, make sure to first have the bitcoin core client running in regtest mode
 *
 * These tests use the same RPC config, as the normal {@link BitcoinJSONRPCClient}
 */
public class IntegrationTestBase {
    static final Logger LOGGER = Logger.getLogger(IntegrationTestBase.class.getName());

    static BitcoindRpcClient client;

    @BeforeClass
    public static void setup() throws Exception {
        // Set logger format used in the tests
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        String login = System.getenv("BTC_NODE_RPC_USER");
        String password = System.getenv("BTC_NODE_RPC_PASSWORD");
        String host = System.getenv("BTC_NODE_HOST");
        String port = System.getenv("BTC_NODE_PORT");
        String urlStr = String.format("http://%s:%s@%s:%s", login, password, host, port);
        client = new BitcoinJSONRPCClient(urlStr);

        ChainVerifier.ensureRunningOnChain(Chain.MAIN, client);
    }

    protected void assertStringNotNullNorEmpty(String s) {
        assertFalse(StringUtils.isEmpty(s));
    }

    protected void assertStringNullOrEmpty(String s) {
        assertTrue(StringUtils.isEmpty(s));
    }
}
