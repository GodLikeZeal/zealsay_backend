package com.zeal.zealsay.dto.request;

import com.zeal.zealsay.common.constant.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


/**
 * 用户修改对象.
 *
 * @author  zhanglei
 * @date 2018/11/15  7:28 PM
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

  @NotNull(message = "用户编号不能为空！")
  private Long id;

  private String username;

  private String name;

  private Integer sex;

  private Integer age;

  private String phoneNumber;

  private String email;

  private String avatar;

  private String address;

  private Integer province;

  private Integer city;

  private Integer area;

  private Role role;

  private Long deptId;

}
