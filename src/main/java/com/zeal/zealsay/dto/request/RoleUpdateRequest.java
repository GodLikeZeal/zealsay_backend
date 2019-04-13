package com.zeal.zealsay.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleUpdateRequest {

  /**
   * id.
   */
  @ApiModelProperty(value = "id",example = "12331")
  @NotNull(message = "id不能为空")
  private Long id;

  /**
   * 角色名称.
   */
  @ApiModelProperty(value = "角色名称",example = "系统管理员")
  @NotBlank(message = "角色名称不能为空")
  private String name;

  /**
   * 角色值.
   */
  @ApiModelProperty(value = "角色值",example = "ADMIN")
  @NotBlank(message = "角色值不能为空")
  private String value;

  /**
   * 描述.
   */
  @ApiModelProperty(value = "描述",example = "拥有最高权限的角色")
  private String description;

}
