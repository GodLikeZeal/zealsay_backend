package com.zeal.zealsay.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
  @TableId(value = "id", type = IdType.ASSIGN_ID)
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
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime startDate;

  /**
   * 结束时间.
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime endDate;

  /**
   * 操作人id.
   */
  private Long operatorId;

  /**
   * 操作人名称.
   */
  private String operatorName;

  /**
   * 操作时间.
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime operatorDate;


}
