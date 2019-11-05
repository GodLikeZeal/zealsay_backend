package com.zeal.zealsay.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;

import com.zeal.zealsay.common.constant.enums.BlockAction;
import com.zeal.zealsay.common.constant.enums.BlockType;
import lombok.*;
import lombok.experimental.Accessors;

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
public class BlockLog implements Serializable {

    /**
     * 主键id.
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 动作 UNSEALING-封禁 BAN-解封.
     */
    private BlockAction action;

    /**
     * 类型.
     */
    private BlockType type;

    /**
     * 目标id.
     */
    private Long targetId;

    /**
     * 目标名称.
     */
    private String targetName;

    /**
     * 缘由.
     */
    private String reason;

    /**
     * 开始时间.
     */
    private LocalDateTime startDate;

    /**
     * 结束时间.
     */
    private LocalDateTime endDate;

    /**
     * 操作人id.
     */
    private Long operatorId;

    /**
     * 操作时间.
     */
    private LocalDateTime operatorDate;


}
