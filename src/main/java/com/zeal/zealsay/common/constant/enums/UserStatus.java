package com.zeal.zealsay.common.constant.enums;

/**
 * 用户状态枚举.
 *
 * @author  zhanglei
 * @date 2019-05-07  15:32
 */
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
