package com.CryptoProject.CryptoInfosys.model;

import com.CryptoProject.CryptoInfosys.config.AttributeEncryptor;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ExchangeAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;

    private String exchange;

    private String label;

    private String baseUrl;

    @Convert(converter = AttributeEncryptor.class)
    private String apiKey;

    @Convert(converter = AttributeEncryptor.class)
    private String apiSecret;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getExchange() {
        return exchange;
    }

    public String getLabel() {
        return label;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setUser(User user) { this.user = user; }
    public void setExchange(String exchange) { this.exchange = exchange; }
    public void setLabel(String label) { this.label = label; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }
    public void setApiSecret(String apiSecret) { this.apiSecret = apiSecret; }
}
