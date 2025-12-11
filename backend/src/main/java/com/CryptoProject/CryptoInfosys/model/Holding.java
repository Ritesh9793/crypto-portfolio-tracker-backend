package com.CryptoProject.CryptoInfosys.model;

import jakarta.persistence.*;

@Entity
@Table(name = "holdings")
public class Holding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Link to user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String asset;
    @Column(name = "asset_symbol", nullable = false)
    private String symbol;

    private Double quantity;
    private Double price;  // buy price or latest updated price

    public Holding() {}

    public Holding(Integer id, User user, String asset, String symbol, Double quantity, Double price) {
        this.id = id;
        this.user = user;
        this.asset = asset;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
    }

    // --- Getters & Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getAsset() { return asset; }
    public void setAsset(String asset) { this.asset = asset; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
