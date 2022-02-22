package com.zeal.zealsay.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
  private Integer code;

  @NotBlank(message = "名称不能为空")
  private String name;

  private String enShort;

  private Integer parentCode;

  @NotBlank(message = "类型不能为空")
  private String type;

  private String description;

  private Integer sort;
}
