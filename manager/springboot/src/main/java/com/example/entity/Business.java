package com.example.entity;

import lombok.Data;
import lombok.Getter;

@Data
public class Business extends Account {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String avatar;
    private String role;
    private String phone;
    private String info;
    private String address;
    private String license;
    private String status;
    private String timeRange;
    private String type;
    private Boolean isCollect;
}
