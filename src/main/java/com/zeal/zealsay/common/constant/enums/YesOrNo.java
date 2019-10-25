package com.zeal.zealsay.common.constant.enums;

/**
 * 是否枚举.
 *
 * @author  zhanglei
 * @date 2019-05-07  15:32
 */
public enum YesOrNo {
    YES(true),
    NO(false)
    ;

    private Boolean description;

    YesOrNo(Boolean description) {
        this.description = description;
    }

    public Boolean getDescription() {
        return description;
    }

    public void setDescription(Boolean description) {
        this.description = description;
    }
}
