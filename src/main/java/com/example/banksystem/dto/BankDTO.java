package com.example.banksystem.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class BankDTO {
    private String bankName;
    private BigDecimal flatFee;
    private BigDecimal percentFee;

    public BankDTO() {}

    public BankDTO(String bankName, BigDecimal flatFee, BigDecimal percentFee) {
        this.bankName = bankName;
        this.flatFee = flatFee;
        this.percentFee = percentFee;
    }


}
