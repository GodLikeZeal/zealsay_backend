package com.zeal.zealsay.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePageRequest {
  /**
   * id.
   */
  private Long id;

  /**
   * 角色名称.
   */
  @ApiModelProperty(value = "角色名称",example = "系统管理员")
  private String name;

  /**
   * 角色值.
   */
  @ApiModelProperty(value = "角色值",example = "ADMIN")
  private String value;

}
