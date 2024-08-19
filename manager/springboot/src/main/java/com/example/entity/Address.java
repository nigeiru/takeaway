package com.example.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    /**  */
    private Integer id;
    /** 收货地址 */
    private String address;
    /** 收货人 */
    private String user;
    /** 联系电话 */
    private String phone;
    /** 用户ID */
    private Integer userId;
}
