package com.zeal.zealsay.dto.request;

import com.zeal.zealsay.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;


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
  @ApiModelProperty(value = "角色名称",example = "系统管理员")
  private String name;

  @NotBlank(message = "角色值不能为空")
  @ApiModelProperty(value = "角色值",example = "ADMIN")
  private String value;

  @ApiModelProperty(value = "描述",example = "整个系统的最高权限")
  private String description;

}
