package com.CryptoProject.CryptoInfosys.dto;

import java.util.List;

public class ExchangeSyncResponse {

    private String exchange;
    private String status;
    private List<ExchangeBalanceResponse> balances;

    public ExchangeSyncResponse(String exchange, String status, List<ExchangeBalanceResponse> balances) {
        this.exchange = exchange;
        this.status = status;
        this.balances = balances;
    }

    public String getExchange() {
        return exchange;
    }

    public String getStatus() {
        return status;
    }

    public List<ExchangeBalanceResponse> getBalances() {
        return balances;
    }
}
