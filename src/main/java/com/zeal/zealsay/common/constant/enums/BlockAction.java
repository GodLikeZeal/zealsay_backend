package com.zeal.zealsay.common.constant.enums;

/**
 * 用户封禁状态.
 *
 * @author  zhanglei
 * @date 2019-05-07  15:42
 */
public enum BlockAction {
    UNSEALING("解封"),
    BAN("封禁"),
    ;

    private String description;

    BlockAction(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
