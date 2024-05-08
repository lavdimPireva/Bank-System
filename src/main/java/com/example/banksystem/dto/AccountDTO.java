package com.example.banksystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class AccountDTO {

    private Long bankId;
    private String userName;
    private BigDecimal initialBalance;

    // Constructors
    public AccountDTO() {
    }

    public AccountDTO(Long bankId, String userName, BigDecimal initialBalance) {
        this.bankId = bankId;
        this.userName = userName;
        this.initialBalance = initialBalance;
    }


}
