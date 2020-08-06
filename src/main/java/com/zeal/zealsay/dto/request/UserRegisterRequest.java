package com.zeal.zealsay.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 用户自主注册请求对象.
 *
 * @author zhanglei
 * @date 2018/11/15  7:08 PM
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

  /**
   * 用户名.
   */
  @NotBlank(message = "用户名不能为空")
  private String username;

  /**
   * 密码.
   */
  @NotBlank(message = "密码不能为空")
  private String password;

  /**
   * 手机号.
   */
  private String phoneNumber;

  /**
   * 邮箱.
   */
  private String email;

}
