package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.tx.RawTransaction;
import com.savl.bitcoin.rpc.client.api.tx.Transaction;
import com.savl.bitcoin.rpc.client.exceptions.GenericRpcException;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

@SuppressWarnings("serial")
class TransactionWrapper extends MapWrapper implements Transaction, Serializable {
    private static final Logger LOGGER = Logger.getLogger(TransactionWrapper.class.getPackage().getName());
    private final BitcoinJSONRPCClient client;

    private RawTransaction raw = null;

    TransactionWrapper(BitcoinJSONRPCClient client, Map<String, ?> m) {
        super(m);
        this.client = client;
    }

    @Override
    public String account() {
        return mapStr("account");
    }

    @Override
    public String address() {
        return mapStr("address");
    }

    @Override
    public String category() {
        return mapStr("category");
    }

    @Override
    public BigDecimal amount() {
        return mapBigDecimal("amount");
    }

    @Override
    public BigDecimal fee() {
        return mapBigDecimal("fee");
    }

    @Override
    public int confirmations() {
        return mapInt("confirmations");
    }

    @Override
    public String blockHash() {
        return mapStr("blockhash");
    }

    @Override
    public int blockIndex() {
        return mapInt("blockindex");
    }

    @Override
    public Date blockTime() {
        return mapDate("blocktime");
    }

    @Override
    public String txId() {
        return mapStr("txid");
    }

    @Override
    public Date time() {
        return mapDate("time");
    }

    @Override
    public Date timeReceived() {
        return mapDate("timereceived");
    }

    @Override
    public String comment() {
        return mapStr("comment");
    }

    @Override
    public String commentTo() {
        return mapStr("to");
    }

    @Override
    public boolean generated() {
        return mapBool("generated");
    }

    @Override
    public RawTransaction raw() {
        if (raw == null)
            try {
                raw = client.getRawTransaction(txId());
            } catch (GenericRpcException ex) {
                LOGGER.warning(ex.getMessage());
            }
        return raw;
    }

    @Override
    public String toString() {
        return m.toString();
    }
}
