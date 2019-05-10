package com.zeal.zealsay.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zeal.zealsay.common.constant.enums.ArticleStatus;
import com.zeal.zealsay.common.constant.enums.Openness;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

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

  @ApiModelProperty(value = "作者名称",example = "张三")
  private String authorName;

  @ApiModelProperty(value = "作者手机号",example = "15171643230")
  private String authorPhone;

  @ApiModelProperty(value = "状态",example = "1")
  private ArticleStatus status;

  @ApiModelProperty(value = "公开度",example = "1")
  private Openness openness;

  @ApiModelProperty(value = "标签",example = "java,IT,云计算,大数据")
  private String label;

  @ApiModelProperty(value = "分类目录id",example = "1231")
  private Long categoryId;

  @ApiModelProperty(value = "作者编号",example = "1")
  private Long authorId;

  @ApiModelProperty(value = "创建开始时间")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  @ApiModelProperty(value = "创建结束时间")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate endDate;

}
