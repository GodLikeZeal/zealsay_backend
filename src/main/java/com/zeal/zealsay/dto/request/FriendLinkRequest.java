package com.zeal.zealsay.dto.request;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 头像.
     */
    @ApiModelProperty(value = "友链头像",example = "https://pan.zealsay.com/20190630223915548000000.jpg")
    private String avatar;

    /**
     * 友链名称.
     */
    @ApiModelProperty(value = "友链名称",example = "阿里云")
    private String friendName;

    /**
     * 友链介绍.
     */
    @ApiModelProperty(value = "友链介绍",example = "国内云计算引领者")
    private String friendInfo;

    /**
     * 链接地址.
     */
    @ApiModelProperty(value = "链接地址",example = "www.aliyun.com")
    private String link;

    /**
     * 印象颜色.
     */
    @ApiModelProperty(value = "印象颜色",example = "info")
    private String color;

    /**
     * 创建时间.
     *
     */
    private LocalDateTime createDate;


}
