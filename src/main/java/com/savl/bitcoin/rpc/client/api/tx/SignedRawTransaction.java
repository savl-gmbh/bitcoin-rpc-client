package com.savl.bitcoin.rpc.client.api.tx;

import java.util.List;

/**
 * See return structure of <a href="https://bitcoincore.org/en/doc/0.18.0/rpc/rawtransactions/signrawtransactionwithkey/">signrawtransactionwithkey</a>
 */
public interface SignedRawTransaction {
    /**
     * @return The hex-encoded raw transaction with signature(s)
     */
    String hex();

    /**
     * @return If the transaction has a complete set of signatures
     */
    boolean complete();

    /**
     * @return Script verification errors (if there are any)
     */
    List<RawTransactionSigningOrVerificationError> errors();
}
