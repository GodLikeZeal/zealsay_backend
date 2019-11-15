package com.zeal.zealsay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zeal.zealsay.common.constant.enums.BlockAction;
import com.zeal.zealsay.common.constant.enums.BlockType;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 封禁解封记录表
 * </p>
 *
 * @author zhanglei
 * @since 2019-03-16
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ArticleLike implements Serializable {

    /**
     * 主键id.
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 文章编号.
     */
    private Long articleId;

    /**
     * 文章名称.
     */
    private String articleName;

    /**
     * 用户id.
     */
    private Long userId;

    /**
     * 用户名称.
     */
    private String userName;

    /**
     * 创建时间.
     */
    private LocalDateTime createDate;

}
