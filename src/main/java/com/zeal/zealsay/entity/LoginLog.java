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
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 登录记录表
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
public class LoginLog implements Serializable {


  /**
   * 主键id.
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private Long id;

  /**
   * 用户id.
   */
  private Long userId;

  /**
   * 用户名称.
   */
  private String username;

  /**
   * 设备类型.
   */
  private String device;

  /**
   * 登录时间.
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime loginDate;

  /**
   * ip地址.
   */
  private String ip;

  /**
   * 国家.
   */
  private String country;

  /**
   * 省.
   */
  private String province;

  /**
   * 市.
   */
  private String city;

  /**
   * 区.
   */
  private String area;

  /**
   * 坐标.
   */
  private String coordinate;


}
