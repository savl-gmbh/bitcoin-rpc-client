package com.savl.bitcoin.rpc.client.api.tx;

/**
 * See error array in return structure of <a href="https://bitcoincore.org/en/doc/0.18.0/rpc/rawtransactions/signrawtransactionwithkey/">signrawtransactionwithkey</a>
 */
public interface RawTransactionSigningOrVerificationError {
    /**
     * @return The hash of the referenced, previous transaction
     */
    String txId();

    /**
     * @return The index of the output to be spent and used as input
     */
    int vOut();

    /**
     * @return The hex-encoded signature script
     */
    String scriptSig();

    /**
     * @return Script sequence number
     */
    int n();

    /**
     * @return Verification or signing error related to the input
     */
    String error();
}
