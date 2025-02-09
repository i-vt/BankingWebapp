package com.example.platform.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Transaction {

    @Id
    private UUID id;

    private String type;

    private BigDecimal amount;

    private LocalDateTime timestamp;

    @JsonBackReference  // Prevent circular reference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    // Constructors, Getters and Setters
    public Transaction() {
        this.id = UUID.randomUUID();
        this.timestamp = LocalDateTime.now();
    }

    public Transaction(String type, BigDecimal amount, Account account) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.amount = amount;
        this.account = account;
        this.timestamp = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
