package com.example.entity;

import lombok.Data;

@Data
public class Banner {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    /** 图片 */
    private String img;
    /** 商家ID */
    private Integer businessId;
    /** 商家名称 */
    private String businessName;

}
