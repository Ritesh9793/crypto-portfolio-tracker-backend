package com.CryptoProject.CryptoInfosys.dto;

import java.math.BigDecimal;

public class ExchangeBalanceResponse {

    private String asset;
    private BigDecimal free;
    private BigDecimal locked;

    public ExchangeBalanceResponse() {
    }

    public ExchangeBalanceResponse(String asset, BigDecimal free, BigDecimal locked) {
        this.asset = asset;
        this.free = free;
        this.locked = locked;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public BigDecimal getFree() {
        return free;
    }

    public void setFree(BigDecimal free) {
        this.free = free;
    }

    public BigDecimal getLocked() {
        return locked;
    }

    public void setLocked(BigDecimal locked) {
        this.locked = locked;
    }
}
