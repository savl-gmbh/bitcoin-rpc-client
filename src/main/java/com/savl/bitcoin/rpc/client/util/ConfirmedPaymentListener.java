/*
 * Copyright (c) 2013, Mikhail Yevchenko. All rights reserved. PROPRIETARY/CONFIDENTIAL.
 */
package com.savl.bitcoin.rpc.client.util;

import com.savl.bitcoin.rpc.client.api.tx.Transaction;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mikhail Yevchenko m.ṥῥẚɱ.ѓѐḿởύḙ@azazar.com
 */
public abstract class ConfirmedPaymentListener extends SimpleBitcoinPaymentListener {

    public int minConf;
    protected Set<String> processed = Collections.synchronizedSet(new HashSet<String>());

    public ConfirmedPaymentListener(int minConf) {
        this.minConf = minConf;
    }

    public ConfirmedPaymentListener() {
        this(6);
    }

    protected boolean markProcess(String txId) {
        return processed.add(txId);
    }

    @Override
    public void transaction(Transaction transaction) {
        if (transaction.confirmations() < minConf)
            return;
        if (!markProcess(transaction.txId()))
            return;
        confirmed(transaction);
    }

    public abstract void confirmed(Transaction transaction);

}