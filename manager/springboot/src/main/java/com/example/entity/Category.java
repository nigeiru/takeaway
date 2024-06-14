package com.example.entity;

import lombok.Data;

@Data
public class Category {
    private Integer id;
    /** 名称 */
    private String name;
    /** 商家ID */
    private Integer businessId;
    /** 商家名称 */
    private String businessName;

}
