package com.zeal.zealsay.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统配置保存对象.
 *
 * @author  zhanglei
 * @date 2018/11/15  6:39 PM
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictSaveRequest {

  @ApiModelProperty(value = "id")
  private Long id;

  @ApiModelProperty(value = "code")
  private Integer code;

  @ApiModelProperty(value = "名称")
  private String name;

}
