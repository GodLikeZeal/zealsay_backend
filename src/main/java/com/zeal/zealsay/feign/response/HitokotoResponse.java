package com.zeal.zealsay.feign.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Hitokoto一言响应实体.
*
* @author  zeal
* @date 2019/6/30 12:14
*/
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HitokotoResponse {
    /**
     * id.
     */
    private Long id;
    /**
     * 正文.
     */
    private String hitokoto;
    /**
     * 类型
     * a	Anime - 动画
     * b	Comic – 漫画
     * c	Game – 游戏
     * d	Novel – 小说
     * e	Myself – 原创
     * f	Internet – 来自网络
     * g	Other – 其他.
     */
    private String type;
    /**
     * 来源.
     */
    private String from;
    /**
     * 添加者.
     */
    private String creator;
    /**
     * 创建时间.
     */
    private Long created_at;
}
