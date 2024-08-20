package com.example.common.enums;

public enum OrderStatusEnum {
    NO_PAY("未支付"),
    CANCELED("已取消"),
    NO_SEND("待发货"),
    NO_RECEIVE("待收货"),
    NO_COMMENT("待评价"),
    DONE("已完成");

    private String value;
    OrderStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
