package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.tx.TxOutput;

import java.math.BigDecimal;

public class BasicTxOutput implements TxOutput {

    private static final long serialVersionUID = 4906609252978270536L;

    public final String address;
    public final String label;
    public final BigDecimal amount;
    public final Boolean spendable;
    public final Boolean solvable;
    public final String desc;
    public final Boolean safe;
    public final byte[] data;

    public BasicTxOutput(String address, BigDecimal amount) {
        this(address, null, amount, null, null, null, null, null);
    }

    public BasicTxOutput(String address, BigDecimal amount, byte[] data) {
        this(address, null, amount, null, null, null, null, data);
    }

    public BasicTxOutput(String address, BigDecimal amount, Boolean spendable, byte[] data) {
        this(address, null, amount, spendable, null, null, null, data);
    }

    public BasicTxOutput(String address, String label, BigDecimal amount, Boolean spendable, Boolean solvable, String desc, Boolean safe, byte[] data) {
        this.address = address;
        this.label = label;
        this.amount = amount;
        this.spendable = spendable;
        this.solvable = solvable;
        this.desc = desc;
        this.safe = safe;
        this.data = data;
    }

    @Override
    public String address() {
        return address;
    }

    @Override
    public BigDecimal amount() {
        return amount;
    }

    @Override
    public byte[] data() {
        return data;
    }

    @Override
    public String label() {
        return label;
    }

    @Override
    public Boolean spendable() {
        return spendable;
    }

    @Override
    public Boolean solvable() {
        return solvable;
    }

    @Override
    public String desc() {
        return desc;
    }

    @Override
    public Boolean safe() {
        return safe;
    }
}
