package com.zeal.zealsay.dto.request;

import com.zeal.zealsay.common.constant.enums.ArticleStatus;
import com.zeal.zealsay.common.constant.enums.Openness;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
public class ArticleAddRequest {

  @ApiModelProperty(value = "标题",example = "震惊，男子半夜不回家竟然是在...")
  private String title;

  @ApiModelProperty(value = "副标题",example = "某男子半夜不回家竟然在办公室加班")
  private String subheading;

  @ApiModelProperty(value = "md格式内容",example = "男子加班成瘾，最后成为人生赢家")
  private String contentMd;

  @ApiModelProperty(value = "html格式内容",example = "男子加班成瘾，最后成为人生赢家")
  private String contentHtml;

  @ApiModelProperty(value = "封面图片",example = "https://pan.zealsay.com/20150318211630_stncE.jpeg")
  private String coverImage;

  @ApiModelProperty(value = "状态",example = "1")
  private ArticleStatus status;

  @ApiModelProperty(value = "公开度",example = "1")
  private Openness openness;

  @ApiModelProperty(value = "标签",example = "java,IT,云计算,大数据")
  private List<String> label;

  @ApiModelProperty(value = "分类目录id",example = "1231")
  private Long categoryId;

}
