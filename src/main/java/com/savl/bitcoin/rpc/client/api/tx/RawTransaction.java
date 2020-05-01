package com.savl.bitcoin.rpc.client.api.tx;

import com.savl.bitcoin.rpc.client.util.MapWrapperType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RawTransaction extends MapWrapperType, Serializable {

    String error();

    String hex();

    String txId();

    int version();

    long lockTime();

    long size();

    long vsize();

    String hash();

    /**
     * This method should be replaced someday
     *
     * @return the list of inputs
     */
    List<In> vIn();

    /**
     * This method should be replaced someday
     *
     * @return the list of outputs
     */
    List<Out> vOut(); // TODO : Create special interface instead of this

    String blockHash();

    /**
     * @return null if this tx has not been confirmed yet
     */
    Integer confirmations();

    /**
     * @return null if this tx has not been confirmed yet
     */
    Date time();

    /**
     * @return null if this tx has not been confirmed yet
     */
    Date blocktime();

    interface In extends TxInput, Serializable {

        Map<String, Object> scriptSig();

        long sequence();

        RawTransaction getTransaction();

        Out getTransactionOutput();
    }

    interface Out extends MapWrapperType, Serializable {

        BigDecimal value();

        int n();

        Out.ScriptPubKey scriptPubKey();

        TxInput toInput();

        RawTransaction transaction();

        interface ScriptPubKey extends MapWrapperType, Serializable {

            String asm();

            String hex();

            int reqSigs();

            String type();

            List<String> addresses();
        }
    }
}
