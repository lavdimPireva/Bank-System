package com.example.banksystem.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
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




}
