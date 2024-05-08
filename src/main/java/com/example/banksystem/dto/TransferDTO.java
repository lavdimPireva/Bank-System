package com.example.banksystem.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferDTO {
    private Long bankId;
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;
    private String description;


    public TransferDTO() {}

    public TransferDTO(Long bankId, Long fromAccountId, Long toAccountId, BigDecimal amount, String description) {
        this.bankId = bankId;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.description = description;
    }


}
