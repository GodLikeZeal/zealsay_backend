package com.zeal.zealsay.common.constant.enums;

public enum UserStatus {
    NORMAL("正常"),
    DISABLED("禁用"),
    LOCK("锁定")
    ;

    private String description;

    UserStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
