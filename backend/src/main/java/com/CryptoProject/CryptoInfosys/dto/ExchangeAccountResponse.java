package com.CryptoProject.CryptoInfosys.dto;

import java.time.LocalDateTime;

public class ExchangeAccountResponse {

    private Long id;
    private String exchange;
    private String label;
    private String baseUrl;
    private String maskedApiKey;
    private boolean hasApiSecret;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getMaskedApiKey() {
        return maskedApiKey;
    }

    public void setMaskedApiKey(String maskedApiKey) {
        this.maskedApiKey = maskedApiKey;
    }

    public boolean isHasApiSecret() {
        return hasApiSecret;
    }

    public void setHasApiSecret(boolean hasApiSecret) {
        this.hasApiSecret = hasApiSecret;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
