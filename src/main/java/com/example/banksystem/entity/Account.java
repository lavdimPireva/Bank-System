package com.example.banksystem.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;
    private String userName;
    private BigDecimal balance;


    public Account() {}

    public Account(String userName, BigDecimal balance) {
        this.userName = userName;
        this.balance = balance;
    }

}

