package com.zeal.zealsay.common.constant.enums;

public enum  Role {
    ADMIN("系统管理员"),
    EDITOR("作者"),
    USER("用户")
    ;

    private String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
