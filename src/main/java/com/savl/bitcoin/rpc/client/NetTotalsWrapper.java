package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.NetTotals;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
class NetTotalsWrapper extends MapWrapper implements NetTotals, Serializable {

    NetTotalsWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public long totalBytesRecv() {
        return mapLong("totalbytesrecv");
    }

    @Override
    public long totalBytesSent() {
        return mapLong("totalbytessent");
    }

    @Override
    public long timeMillis() {
        return mapLong("timemillis");
    }

    @Override
    @SuppressWarnings("unchecked")
    public uploadTarget uploadTarget() {
        return new uploadTargetImpl((Map<String, ?>) m.get("uploadtarget"));
    }

    private class uploadTargetImpl extends MapWrapper implements uploadTarget, Serializable {

        public uploadTargetImpl(Map<String, ?> m) {
            super(m);
        }

        @Override
        public long timeFrame() {
            return mapLong("timeframe");
        }

        @Override
        public int target() {
            return mapInt("target");
        }

        @Override
        public boolean targetReached() {
            return mapBool("targetreached");
        }

        @Override
        public boolean serveHistoricalBlocks() {
            return mapBool("servehistoricalblocks");
        }

        @Override
        public long bytesLeftInCycle() {
            return mapLong("bytesleftincycle");
        }

        @Override
        public long timeLeftInCycle() {
            return mapLong("timeleftincycle");
        }
    }
}
