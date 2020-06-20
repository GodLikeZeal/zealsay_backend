package com.zeal.zealsay.common.constant.enums;

/**
 * 用户封禁状态.
 *
 * @author  zhanglei
 * @date 2019-05-07  15:42
 */
public enum BlockAction {
    REGISTER("注册"),
    UNSEALING("解封"),
    BAN("封禁"),
    UP("上架"),
    DOWN("下架"),
    LIKE_BLOG("喜欢"),
    DISLIKE_BLOG("不喜欢"),
    PUBLISH_BLOG("发布"),
    COMMENT_BLOG("评论"),
    REPLY_BLOG("回复"),
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
