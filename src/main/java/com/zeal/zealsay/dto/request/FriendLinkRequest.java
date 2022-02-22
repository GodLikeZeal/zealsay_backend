package com.zeal.zealsay.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 头像.
     */
    private String avatar;

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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createDate;


}
