package com.zeal.zealsay.dto.request;

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
  private String name;

  /**
   * 角色值.
   */
  private String value;

  /**
   * 描述.
   */
  private String description;

}
