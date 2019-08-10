package com.zeal.zealsay.dto.request;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 友链记录表
 * </p>
 *
 * @author zhanglei
 * @since 2019-07-31
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendLinkRequest {

    /**
     * 主键.
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 头像.
     */
    private String avator;

    /**
     * 友链名称.
     */
    private String friendName;

    /**
     * 友链介绍.
     */
    private String friendInfo;

    /**
     * 链接地址.
     */
    private String link;

    /**
     * 印象颜色.
     */
    private String color;

    /**
     * 创建时间.
     *
     */
    private LocalDateTime createDate;


}
