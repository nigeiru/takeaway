package com.example.entity;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 评价表
 */
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    /** ID */
    private Integer id;
    /** 评论 */
    private String content;
    /** 评分 */
    private Double star;
    /** 用户ID */
    private Integer userId;
    /** 用户名称 */
    private String userName;
    /** 商家iD */
    private Integer businessId;
    /** 商家名称 */
    private String businessName;
    /** 商品ID */
    private Integer goodsId;
    /** 商品名称 */
    private String goodsName;
    /** 订单ID */
    private Integer orderId;
    private String orderNo;// 新增
    private String orderName;
    private String time;
}
