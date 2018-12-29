package com.zeal.zealsay.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 文章分页请求对象.
 *
 * @author  zhanglei
 * @date 2018/12/29  3:23 PM
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePageRequest {

  @ApiModelProperty(value = "标题",example = "震惊，男子半夜不回家竟然是在...")
  private String title;

  @ApiModelProperty(value = "副标题",example = "某男子半夜不回家竟然在办公室加班")
  private String subheading;

  @ApiModelProperty(value = "内容",example = "男子加班成瘾，最后成为人生赢家")
  private String content;

  @ApiModelProperty(value = "状态",example = "1")
  private Integer status;

  @ApiModelProperty(value = "公开度",example = "1")
  private Integer openness;

  @ApiModelProperty(value = "标签",example = "java,IT,云计算,大数据")
  private String label;

  @ApiModelProperty(value = "分类目录id",example = "1231")
  private Long categoryId;

  @ApiModelProperty(value = "作者编号")
  private Long authorId;

  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createAt;

}
