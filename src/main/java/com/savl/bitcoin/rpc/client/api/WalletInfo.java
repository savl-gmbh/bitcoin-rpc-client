package com.savl.bitcoin.rpc.client.api;

import com.savl.bitcoin.rpc.client.util.MapWrapperType;

import java.io.Serializable;
import java.math.BigDecimal;

public interface WalletInfo extends MapWrapperType, Serializable {

    long walletVersion();

    BigDecimal balance();

    BigDecimal unconfirmedBalance();

    BigDecimal immatureBalance();

    long txCount();

    long keyPoolOldest();

    long keyPoolSize();

    long unlockedUntil();

    BigDecimal payTxFee();

    String hdMasterKeyId();
}
