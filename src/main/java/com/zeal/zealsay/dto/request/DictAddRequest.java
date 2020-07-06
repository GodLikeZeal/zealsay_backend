package com.zeal.zealsay.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 数据字典新增对象.
 *
 * @author  zhanglei
 * @date 2018/11/15  6:39 PM
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictAddRequest {

  @NotNull(message = "code不能为空")
  @ApiModelProperty(value = "编码")
  private Integer code;

  @NotBlank(message = "名称不能为空")
  @ApiModelProperty(value = "名称")
  private String name;

  @ApiModelProperty(value = "拼音简称")
  private String enShort;

  @ApiModelProperty(value = "父层code")
  private Integer parentCode;

  @NotBlank(message = "类型不能为空")
  @ApiModelProperty(value = "类型")
  private String type;

  @ApiModelProperty(value = "描述")
  private String description;

  @ApiModelProperty(value = "顺序")
  private Integer sort;
}
