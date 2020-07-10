package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.tx.UnspentTxOutput;
import com.savl.bitcoin.rpc.client.api.tx.UtxoSet;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class UtxoSetWrapper extends MapWrapper implements UtxoSet, Serializable {

    public UtxoSetWrapper(Map<String, ?> m) {
        super(m);
    }


    @Override
    public Integer searchedItems() {
        return mapInt("searched_items");
    }

    @Override
    public BigDecimal totalAmount() {
        return mapBigDecimal("total_amount");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UnspentTxOutput> unspents() {
        List<Map<String, ?>> maps = (List<Map<String, ?>>) m.get("unspents");
        List<UnspentTxOutput> utxoList = new LinkedList<UnspentTxOutput>();
        for (Map<String, ?> m : maps) {
            UnspentTxOutputWrapper add = new UnspentTxOutputWrapper(m);
            utxoList.add(add);
        }
        return utxoList;
    }
}