package com.zeal.zealsay.dto.request;

import com.zeal.zealsay.common.constant.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 用户注册请求对象.
 *
 * @author zhanglei
 * @date 2018/11/15  7:08 PM
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddRequest {

  /**
   * 用户名.
   */
  @NotBlank(message = "用户名不能为空")
  private String username;

  /**
   * 真实名称.
   */
  private String name;

  /**
   * 密码.
   */
  @NotBlank(message = "密码不能为空")
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

}
