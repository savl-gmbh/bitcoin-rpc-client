package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.Block;
import com.savl.bitcoin.rpc.client.exceptions.GenericRpcException;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
class BlockMapWrapper extends MapWrapper implements Block, Serializable {


    private final BitcoinJSONRPCClient client;

    BlockMapWrapper(BitcoinJSONRPCClient client, Map<String, ?> m) {
        super(m);
        this.client = client;
    }

    @Override
    public String hash() {
        return mapStr("hash");
    }

    @Override
    public int confirmations() {
        return mapInt("confirmations");
    }

    @Override
    public int size() {
        return mapInt("size");
    }

    @Override
    public int height() {
        return mapInt("height");
    }

    @Override
    public int version() {
        return mapInt("version");
    }

    @Override
    public String merkleRoot() {
        return mapStr("merkleroot");
    }

    @Override
    public String chainwork() {
        return mapStr("chainwork");
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> tx() {
        return (List<String>) m.get("tx");
    }

    @Override
    public Date time() {
        return mapDate("time");
    }

    @Override
    public long nonce() {
        return mapLong("nonce");
    }

    @Override
    public String bits() {
        return mapStr("bits");
    }

    @Override
    public BigDecimal difficulty() {
        return mapBigDecimal("difficulty");
    }

    @Override
    public String previousHash() {
        return mapStr("previousblockhash");
    }

    @Override
    public String nextHash() {
        return mapStr("nextblockhash");
    }

    @Override
    public Block previous() throws GenericRpcException {
        if (!m.containsKey("previousblockhash"))
            return null;
        return client.getBlock(previousHash());
    }

    @Override
    public Block next() throws GenericRpcException {
        if (!m.containsKey("nextblockhash"))
            return null;
        return client.getBlock(nextHash());
    }

}
