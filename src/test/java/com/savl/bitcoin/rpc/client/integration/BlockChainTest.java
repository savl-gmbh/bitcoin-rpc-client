package com.savl.bitcoin.rpc.client.integration;

import com.savl.bitcoin.rpc.client.api.BlockChainInfo;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Integration tests for the Blockchain command group
 *
 * See <a href="https://bitcoincore.org/en/doc/0.18.0/rpc/">Bitcoin Core RPC documentation</a>
 */
public class BlockChainTest extends IntegrationTestBase {
    @Test(expected = Test.None.class) // no exception expected
    public void getBlockChainInfoTest() {
        BlockChainInfo bci = client.getBlockChainInfo();

        // Check if mandatory fields are present and have valid values
        assertStringNotNullNorEmpty(bci.chain());
        assertTrue(bci.blocks() >= 0);
        assertTrue(bci.headers() >= 0);
        assertStringNotNullNorEmpty(bci.bestBlockHash());
        assertTrue(bci.difficulty().compareTo(BigDecimal.ZERO) >= 0);
        assertTrue(bci.medianTime() > 0);
        assertTrue(bci.verificationProgress().intValue() >= 0 && bci.verificationProgress().intValue() <= 1); // interval [0..1]
        assertNotNull(bci.initialBlockDownload()); // Depending on how soon the test runs after regtest initialization, it may be true or false
        assertStringNotNullNorEmpty(bci.chainWork());
        assertTrue(bci.sizeOnDisk() > 0);
        assertFalse(bci.pruned()); // no pruning enabled for tests
        assertStringNullOrEmpty(bci.warnings());
    }
}
