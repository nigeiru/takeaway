package com.example.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrdersDTO {
    private Integer businessId;
    // 收货信息
    private String user;
    private String address;
    private String phone;
    // 订单备注信息
    private String comment;

    private String payType;
}
