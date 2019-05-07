package com.zeal.zealsay.common.constant.enums;

/**
 * 省市区枚举.
 *
 * @author  zhanglei
 * @date 2019-05-07  15:42
 */
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
