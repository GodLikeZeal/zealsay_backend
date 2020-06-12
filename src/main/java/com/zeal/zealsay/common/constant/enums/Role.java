package com.zeal.zealsay.common.constant.enums;

/**
 * 这里是注释.
 *
 * @author  zhanglei
 * @date 2019-05-07  15:34
 */
public enum  Role {
    ROLE_ADMIN("系统管理员"),
    ROLE_EDITOR("作者"),
    ROLE_USER("用户"),
    ROLE_EXPERIENCER("体验者")
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
