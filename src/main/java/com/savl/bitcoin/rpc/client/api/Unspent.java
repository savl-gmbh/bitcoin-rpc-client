package com.savl.bitcoin.rpc.client.api;

import com.savl.bitcoin.rpc.client.api.tx.TxInput;
import com.savl.bitcoin.rpc.client.api.tx.TxOutput;

import java.io.Serializable;

/**
 * Also known as UTXO (unspent transaction output)
 * <br><br>
 * Is a type of transaction output (therefore implements {@link TxOutput}.
 * <br><br>
 * But it can also be used as a transaction input (therefore implements {@link TxInput}).
 *
 * See {@link BitcoindRpcClient#listUnspent()}
 */
public interface Unspent extends TxInput, TxOutput, Serializable {

    /**
     * @deprecated Use {@link TxOutput#address()} instead
     *
     * @return account
     */
    @Deprecated
    String account();

    Integer confirmations();

    /**
     * @return The redeemScript if scriptPubKey is P2SH
     */
    String redeemScript();

    /**
     * @return witnessScript, if the scriptPubKey is P2WSH or P2SH-P2WSH
     */
    String witnessScript();
}
