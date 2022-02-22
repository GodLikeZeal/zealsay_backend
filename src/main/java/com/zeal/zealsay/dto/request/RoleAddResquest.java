package com.zeal.zealsay.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


/**
 * 角色新增对象.
 *
 * @author  zhanglei
 * @date 2018/11/15  7:19 PM
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleAddResquest {

  @NotBlank(message = "角色名称不能为空")
  private String name;

  @NotBlank(message = "角色值不能为空")
  private String value;

  private String description;

}
