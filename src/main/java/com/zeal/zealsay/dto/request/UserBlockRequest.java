package com.zeal.zealsay.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 用户封禁请求对象.
 *
 * @author zhanglei
 * @date 2018/11/15  7:08 PM
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBlockRequest {

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
  private Long roleId;

  /**
   * 部门id.
   */
  private Long deptId;

}
