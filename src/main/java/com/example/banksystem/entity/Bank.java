package com.example.banksystem.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bankId;
    private String bankName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> accounts;

    private BigDecimal totalTransactionFeeAmount;
    private BigDecimal totalTransferAmount;

    // both types of fee amount
    private BigDecimal transactionFlatFeeAmount;
    private BigDecimal transactionPercentFeeValue;


    public Bank(String bankName, BigDecimal transactionFlatFeeAmount, BigDecimal transactionPercentFeeValue) {
        this.bankName = bankName;
        this.transactionFlatFeeAmount = transactionFlatFeeAmount;
        this.transactionPercentFeeValue = transactionPercentFeeValue;
        this.accounts = new ArrayList<>();
        this.totalTransactionFeeAmount = BigDecimal.ZERO;
        this.totalTransferAmount = BigDecimal.ZERO;
    }


    public Bank() {

    }
}
