package com.example.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AmountDTO {
    private BigDecimal amount;
    private BigDecimal discount;
    private BigDecimal actual;
}
