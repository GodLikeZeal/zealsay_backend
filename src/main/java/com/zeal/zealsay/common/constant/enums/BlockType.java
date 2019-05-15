package com.zeal.zealsay.common.constant.enums;

/**
 * 用户封禁状态.
 *
 * @author  zhanglei
 * @date 2019-05-07  15:42
 */
public enum BlockType {
    USER("用户"),
    ARTICLE("文章作品"),
    ;

    private String description;

    BlockType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
