package com.zeal.zealsay.common.constant.enums;

public enum DictType {
    REGION("省市区")
    ;

    private String description;

    DictType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
