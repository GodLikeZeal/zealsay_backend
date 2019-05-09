package com.zeal.zealsay.common.constant.enums;

/**
 * 用户状态枚举.
 *
 * @author  zhanglei
 * @date 2019-05-07  15:32
 */
public enum ArticleStatus {
    DRAFT("草稿"),
    FORMAL("正式发布"),
    DOWN("下架")
    ;

    private String description;

    ArticleStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
