package com.zeal.zealsay.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.zeal.zealsay.common.constant.enums.Role;
import com.zeal.zealsay.common.constant.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 用户实体.
 *
 * @author zhanglei
 * @date 2018/11/15  6:46 PM
 */
@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
@Accessors(chain = true)
public class User implements Serializable {

  /**
   * id.
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private Long id;

  /**
   * 用户名.
   */
  private String username;

  /**
   * 真实名称.
   */
  private String name;

  /**
   * 密码.
   */
  private String password;

  /**
   * 性别.
   */
  private Integer sex;

  /**
   * 年龄.
   */
  private Integer age;

  /**
   * 简介.
   */
  private String introduction;

  /**
   * 标签.
   */
  private String label;

  /**
   * 手机号.
   */
  private String phoneNumber;

  /**
   * 邮箱.
   */
  private String email;

  /**
   * 邮箱是否验证过.
   */
  private Boolean emailConfirm;

  /**
   * 头像.
   */
  private String avatar;

  /**
   * 地址.
   */
  private String address;

  /**
   * 省.
   */
  private Integer province;

  /**
   * 市.
   */
  private Integer city;

  /**
   * 区.
   */
  private Integer area;

  /**
   * 角色.
   */
  private Role role;

  /**
   * 部门id.
   */
  private Long deptId;

  /**
   * 状态.
   */
  private UserStatus status;

  /**
   * 注册时间.
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime registerDate;

  /**
   * 最后密码修改时间.
   */
  private Date lastPasswordResetDate;

  /**
   * 更新时间.
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime updateDate;
}
