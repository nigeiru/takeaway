package com.example.entity;

import lombok.Data;

@Data
public class Collect {
    /** ID */
    private Integer id;
    /** 商家ID */
    private Integer businessId;
    /** 商家名称 */
    private String businessName;
    /** 用户ID */
    private Integer userId;
    /** 用户名称 */
    private String userName;
    /** 收藏时间 */
    private String time;
}
