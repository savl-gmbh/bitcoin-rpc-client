package com.savl.bitcoin.rpc.client;

import java.io.Serializable;

/**
 * Input object for scantxoutset operation
 */
@SuppressWarnings("serial")
public class ScanObject implements Serializable {

    private String descriptor;
    private Integer range;

    public ScanObject(String descriptor, Integer range) {
        this.descriptor = descriptor;
        this.range = range;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

}