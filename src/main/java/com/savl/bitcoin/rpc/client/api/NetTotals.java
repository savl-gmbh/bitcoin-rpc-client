package com.savl.bitcoin.rpc.client.api;

import com.savl.bitcoin.rpc.client.util.MapWrapperType;

import java.io.Serializable;

public interface NetTotals extends MapWrapperType, Serializable {

    long totalBytesRecv();

    long totalBytesSent();

    long timeMillis();

    uploadTarget uploadTarget();

    interface uploadTarget extends MapWrapperType, Serializable {

        long timeFrame();

        int target();

        boolean targetReached();

        boolean serveHistoricalBlocks();

        long bytesLeftInCycle();

        long timeLeftInCycle();
    }
}
