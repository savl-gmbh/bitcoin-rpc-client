package com.savl.bitcoin.rpc.client;

import com.savl.bitcoin.rpc.client.api.WalletInfo;
import com.savl.bitcoin.rpc.client.util.MapWrapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@SuppressWarnings("serial")
class WalletInfoWrapper extends MapWrapper implements WalletInfo, Serializable {

    WalletInfoWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public long walletVersion() {
        return mapLong("walletversion");
    }

    @Override
    public BigDecimal balance() {
        return mapBigDecimal("balance");
    }

    @Override
    public BigDecimal unconfirmedBalance() {
        return mapBigDecimal("unconfirmed_balance");
    }

    @Override
    public BigDecimal immatureBalance() {
        return mapBigDecimal("immature_balance");
    }

    @Override
    public long txCount() {
        return mapLong("txcount");
    }

    @Override
    public long keyPoolOldest() {
        return mapLong("keypoololdest");
    }

    @Override
    public long keyPoolSize() {
        return mapLong("keypoolsize");
    }

    @Override
    public long unlockedUntil() {
        return mapLong("unlocked_until");
    }

    @Override
    public BigDecimal payTxFee() {
        return mapBigDecimal("paytxfee");
    }

    @Override
    public String hdMasterKeyId() {
        return mapStr("hdmasterkeyid");
    }
}
