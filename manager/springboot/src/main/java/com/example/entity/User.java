package com.example.entity;

import lombok.Data;

import java.io.Serializable;

@Data

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**  */
    private Integer id;
    /** 账号 */
    private String username;
    /** 密码 */
    private String password;
    /** 姓名 */
    private String name;
    /** 头像 */
    private String avatar;
    /** 角色 */
    private String role;
    /** 性别 */
    private String sex;
    /** 电话 */
    private String phone;
}
