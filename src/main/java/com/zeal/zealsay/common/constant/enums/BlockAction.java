package com.zeal.zealsay.common.constant.enums;

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
