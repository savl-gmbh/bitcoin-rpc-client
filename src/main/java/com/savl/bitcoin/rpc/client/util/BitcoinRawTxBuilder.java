/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.savl.bitcoin.rpc.client.util;

import com.savl.bitcoin.rpc.client.BasicTxInput;
import com.savl.bitcoin.rpc.client.BasicTxOutput;
import com.savl.bitcoin.rpc.client.api.BitcoindRpcClient;
import com.savl.bitcoin.rpc.client.api.Unspent;
import com.savl.bitcoin.rpc.client.api.tx.RawTransaction;
import com.savl.bitcoin.rpc.client.api.tx.TxInput;
import com.savl.bitcoin.rpc.client.api.tx.TxOutput;
import com.savl.bitcoin.rpc.client.exceptions.GenericRpcException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author azazar
 */
public class BitcoinRawTxBuilder {

    public final BitcoindRpcClient bitcoin;
    private final HashMap<String, RawTransaction> txCache = new HashMap<>();
    public Set<TxInput> inputs = new LinkedHashSet<>();
    public List<TxOutput> outputs = new ArrayList<>();
    public List<String> privateKeys;

    public BitcoinRawTxBuilder(BitcoindRpcClient bitcoin) {
        this.bitcoin = bitcoin;
    }

    public BitcoinRawTxBuilder in(TxInput in) {
        inputs.add(new Input(in.txid(), in.vout()));
        return this;
    }

    public BitcoinRawTxBuilder in(String txid, int vout) {
        in(new BasicTxInput(txid, vout));
        return this;
    }

    public BitcoinRawTxBuilder out(String address, BigDecimal amount) {
        return out(address, amount, null);
    }

    public BitcoinRawTxBuilder out(String address, BigDecimal amount, byte[] data) {
        outputs.add(new BasicTxOutput(address, amount, data));
        return this;
    }

    public BitcoinRawTxBuilder in(BigDecimal value) throws GenericRpcException {
        return in(value, 6);
    }

    public BitcoinRawTxBuilder in(BigDecimal value, int minConf) throws GenericRpcException {
        List<Unspent> unspent = bitcoin.listUnspent(minConf);
        BigDecimal v = value;
        for (Unspent o : unspent) {
            if (!inputs.contains(new Input(o))) {
                in(o);
                v = v.subtract(o.amount());
            }
            if (v.compareTo(BigDecimal.ZERO) < 0)
                break;
        }
        if (BigDecimal.ZERO.compareTo(v) < 0)
            throw new GenericRpcException("Not enough bitcoins (" + v + "/" + value + ")");
        return this;
    }

    private RawTransaction tx(String txId) throws GenericRpcException {
        RawTransaction tx = txCache.get(txId);
        if (tx != null)
            return tx;
        tx = bitcoin.getRawTransaction(txId);
        txCache.put(txId, tx);
        return tx;
    }

    public BitcoinRawTxBuilder outChange(String address) throws GenericRpcException {
        return outChange(address, BigDecimal.ZERO);
    }

    public BitcoinRawTxBuilder outChange(String address, BigDecimal fee) throws GenericRpcException {
        BigDecimal is = BigDecimal.ZERO;
        for (TxInput i : inputs)
            is = is.add(tx(i.txid()).vOut().get(i.vout()).value());
        BigDecimal os = fee;
        for (TxOutput o : outputs)
            os = os.add(o.amount());
        if (os.compareTo(is) < 0)
            out(address, is.subtract(os));
        return this;
    }

    public BitcoinRawTxBuilder addPrivateKey(String privateKey) {
        if (privateKeys == null)
            privateKeys = new ArrayList<String>();
        privateKeys.add(privateKey);
        return this;
    }

    public String create() throws GenericRpcException {
        return bitcoin.createRawTransaction(new ArrayList<>(inputs), outputs);
    }

    /**
     * @deprecated Underlying client call not supported anymore, use instead
     * {@link BitcoindRpcClient#signRawTransactionWithKey(String, List, List, SignatureHashType)}
     *
     * @return Transaction id
     */
    @Deprecated
    public String sign() throws GenericRpcException {
        return bitcoin.signRawTransaction(create(), null, privateKeys);
    }

    /**
     * @deprecated Relies on call to deprecated {@link #sign()}. Instead, call
     * {@link BitcoindRpcClient#sendRawTransaction(String)} on a signed
     * transaction
     *
     * @return Transaction id
     */
    @Deprecated
    public String send() throws GenericRpcException {
        return bitcoin.sendRawTransaction(sign());
    }

    @SuppressWarnings("serial")
    private class Input extends BasicTxInput {

        public Input(String txid, Integer vout) {
            super(txid, vout);
        }

        public Input(TxInput copy) {
            this(copy.txid(), copy.vout());
        }

        @Override
        public int hashCode() {
            return txid.hashCode() + vout;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (!(obj instanceof TxInput))
                return false;
            TxInput other = (TxInput) obj;
            return vout == other.vout() && txid.equals(other.txid());
        }

    }

}
