package com.zeal.zealsay.common.entity;

import com.zeal.zealsay.entity.Role;
import com.zeal.zealsay.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Builder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserVo {

  @ApiModelProperty(value = "用户")
  private User user;

  @ApiModelProperty(value = "角色")
  private Role role;

}
