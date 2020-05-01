package com.savl.bitcoin.rpc.client;

import java.math.BigDecimal;

/**
 * In addition to {@link BasicTxInput}, this also includes a
 * {@link #redeemScript} and {@link #witnessScript}.
 * <br>
 * <br>
 *
 * With the addition of these fields, the {@link ExtendedTxInput} can represent
 * inputs for for P2SH, P2SH-P2WPKH, P2SH-P2WSH
 *
 * See <a href=
 * "https://bitcoincore.org/en/segwit_wallet_dev/#creation-of-p2sh-p2wsh-address">Bitcoin
 * Core documentation on P2SH and P2WSH addresses</a>
 * See <a href=
 * "https://bitcoincore.org/en/doc/0.18.0/rpc/rawtransactions/signrawtransactionwithkey/">Bitcoin
 * Core RPC documentation of signrawtransactionwithkey</a>
 * , where the
 * different scenarios for the extra fields of txIns (prevtxs) are
 * specified
 */
@SuppressWarnings("serial")
public
class ExtendedTxInput extends BasicTxInput {

    private final String redeemScript;
    private final String witnessScript;

    public ExtendedTxInput(String txid, int vout, String scriptPubKey, BigDecimal amount, String redeemScript, String witnessScript) {
        super(txid, vout, scriptPubKey, amount);
        this.redeemScript = redeemScript;
        this.witnessScript = witnessScript;
    }

    public String redeemScript() {
        return redeemScript;
    }

    public String witnessScript() {
        return witnessScript;
    }

}
