package com.zeal.zealsay.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 文章添加请求对象.
 *
 * @author  zhanglei
 * @date 2018/12/29  3:23 PM
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCategoryAddRequest {

  @NotBlank(message = "名称不能为空")
  @ApiModelProperty(value = "名称",example = "奇淫技巧")
  private String name;

  @ApiModelProperty(value = "别名",example = "tec")
  private String alias;

  @ApiModelProperty(value = "说明",example = "记录技术分享")
  private String description;

  @ApiModelProperty(value = "父层id",example = "")
  private Long parentId;

}
