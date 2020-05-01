package com.savl.bitcoin.rpc.client.api;

import com.savl.bitcoin.rpc.client.exceptions.GenericRpcException;
import com.savl.bitcoin.rpc.client.util.MapWrapperType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface Block extends MapWrapperType, Serializable {

    String hash();

    int confirmations();

    int size();

    int height();

    int version();

    String merkleRoot();

    List<String> tx();

    Date time();

    long nonce();

    String bits();

    BigDecimal difficulty();

    String previousHash();

    String nextHash();

    String chainwork();

    Block previous() throws GenericRpcException;

    Block next() throws GenericRpcException;
}
