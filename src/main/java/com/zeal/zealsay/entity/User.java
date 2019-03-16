package com.zeal.zealsay.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.zeal.zealsay.common.constant.enums.Role;
import com.zeal.zealsay.common.constant.enums.UserStatus;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户实体.
 *
 * @author zhanglei
 * @date 2018/11/15  6:46 PM
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class User {

  /**
   * id.
   */
  @TableId(value = "id", type = IdType.ID_WORKER)
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
  private LocalDateTime registerDate;

  /**
   * 最后密码修改时间.
   */
  private Date lastPasswordResetDate;

  /**
   * 更新时间.
   */
  private  LocalDateTime updateDate;
}
