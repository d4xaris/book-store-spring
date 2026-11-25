package com.d4xaris.bookstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "CLIENTS")
@Getter
@Setter
@NoArgsConstructor
public class Client extends User {

    private BigDecimal balance;

    public Client(Long id, String email, String password, String name, BigDecimal balance) {
        super(id, email, password, name);
        this.balance = balance;
    }
}