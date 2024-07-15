package com.example.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrdersItem {
    private static final long serialVersionUID = 1L;

    /**  */
    private Integer id;
    /** 订单ID */
    private Integer orderId;
    /** 商品名称 */
    private String goodsName;
    /** 商品图片 */
    private String goodsImg;
    /** 商品价格 */
    private BigDecimal price;
    /** 购买数量 */
    private Integer num;


}
