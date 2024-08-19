package com.example.entity;

import lombok.Data;

@Data
public class Cart {
    /** ID */
    private Integer id;
    /** 商品ID */
    private Integer goodsId;
    /** 数量 */
    private Integer num;
    /** 用户ID */
    private Integer userId;
    private Integer businessId;
    private Goods goods;
    private Business business;
}
