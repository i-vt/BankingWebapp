package com.example.platform.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "app_user")  // Avoid 'user' reserved keyword
public class User {

    @Id
    private UUID id;

    private String name;

    @JsonIgnore  // Prevent recursive serialization
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Account> accounts;

    // Constructors, Getters and Setters
    public User() {
        this.id = UUID.randomUUID();  // Manually generate UUID
    }

    public User(String name) {
        this.id = UUID.randomUUID();  // Manually generate UUID
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
