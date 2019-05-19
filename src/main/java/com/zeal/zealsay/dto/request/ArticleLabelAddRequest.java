package com.zeal.zealsay.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


/**
 * 标签云添加请求对象.
 *
 * @author  zhanglei
 * @date 2018/12/29  3:23 PM
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleLabelAddRequest {

  @NotBlank(message = "名称不能为空")
  @ApiModelProperty(value = "名称")
  private String name;

  @ApiModelProperty(value = "图标")
  private String icon;

  @ApiModelProperty(value = "热度")
  private Long hot;

  @ApiModelProperty(value = "外层颜色")
  private String outColor;

  @ApiModelProperty(value = "头像颜色")
  private String avatarColor;

}
