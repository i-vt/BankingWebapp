package com.example.platform.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionDTO {
    private UUID id;
    private String type;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String accountNumber;

    // Constructor
    public TransactionDTO(UUID id, String type, BigDecimal amount, LocalDateTime timestamp, String accountNumber) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.accountNumber = accountNumber;
    }

    // Getters and Setters
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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
