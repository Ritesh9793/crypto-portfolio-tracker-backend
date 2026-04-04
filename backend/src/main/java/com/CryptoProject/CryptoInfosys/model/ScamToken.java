package com.CryptoProject.CryptoInfosys.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "scam_tokens")
public class ScamToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contractAddress;

    @Column(nullable = false)
    private String chain;

    @Column(nullable = false)
    private String riskLevel;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private LocalDateTime lastSeen = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }
}
