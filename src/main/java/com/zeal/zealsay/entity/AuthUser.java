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
 * 第三方登录用户信息
 * </p>
 *
 * @author zhanglei
 * @since 2019-09-12
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AuthUser implements Serializable {

  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private Long id;

  /**
   * uuid.
   */
  private String uid;

  /**
   * 用户名.
   */
  private String username;

  /**
   * 昵称.
   */
  private String nickname;

  /**
   * 头像.
   */
  private String avatar;

  /**
   * 博客.
   */
  private String blog;

  /**
   * 公司.
   */
  private String company;

  /**
   * 位置.
   */
  private String location;

  /**
   * 邮箱.
   */
  private String email;

  /**
   * 个人说明.
   */
  private String remark;

  /**
   * 性别.
   */
  private String gender;

  /**
   * 来源.
   */
  private String source;

  /**
   * 是否绑定过.
   */
  private Boolean bind;

  /**
   * 用户id.
   */
  private Long userId;

  /**
   * 创建时间.
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime createDate;


}
