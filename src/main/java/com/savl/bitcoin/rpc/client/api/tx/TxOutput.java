package com.savl.bitcoin.rpc.client.api.tx;

import java.io.Serializable;
import java.math.BigDecimal;

public interface TxOutput extends Serializable {

    String address();

    /**
     * @return The label associated with {@link #address()}
     */
    String label();

    BigDecimal amount();

    /**
     * @return Whether we have the private keys to spend this output
     */
    Boolean spendable();

    /**
     * @return Whether we know how to spend this output, ignoring the lack of keys
     */
    Boolean solvable();

    /**
     * @return (only when solvable) A descriptor for spending this output
     */
    String desc();

    /**
     * @return Whether this output is considered safe to spend. Unconfirmed
     * transactions from outside keys and unconfirmed replacement
     * transactions are considered unsafe and are not eligible for spending
     * by fundrawtransaction and sendtoaddress.
     */
    Boolean safe();

    byte[] data();
}
