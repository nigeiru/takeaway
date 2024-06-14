package com.example.entity;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class Goods {
    /** ID */
    private Integer id;
    /** 名称 */
    private String name;
    /** 价格 */
    private BigDecimal price;
    /** 图片 */
    private String img;
    /** 描述 */
    private String descr;
    /** 原材料 */
    private String origin;
    /** 口味 */
    private String taste;
    /** 规格 */
    private String specs;
    /** 上架日期 */
    private String date;
    /** 上架状态 */
    private String status;
    /** 折扣 */
    private Double discount;
    /** 商家ID */
    private Integer businessId;
    /** 分类ID */
    private Integer categoryId;

}
