package com.zeal.zealsay.dto.request;

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
  @NotNull(message = "id不能为空")
  private Long id;

  /**
   * 角色名称.
   */
  @NotBlank(message = "角色名称不能为空")
  private String name;

  /**
   * 角色值.
   */
  @NotBlank(message = "角色值不能为空")
  private String value;

  /**
   * 描述.
   */
  private String description;

}
