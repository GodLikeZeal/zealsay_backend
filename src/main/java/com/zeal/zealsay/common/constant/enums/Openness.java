package com.zeal.zealsay.common.constant.enums;

/**
 * 公开度.
 *
 * @author  zhanglei
 * @date 2019-05-07  15:32
 */
public enum Openness {
    SELFONLY("仅自己"),
    ALL("所有人")
    ;

    private String description;

    Openness(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
