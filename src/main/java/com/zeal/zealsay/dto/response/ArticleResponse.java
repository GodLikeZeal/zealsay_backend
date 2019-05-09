package com.zeal.zealsay.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.zeal.zealsay.common.constant.enums.ArticleStatus;
import com.zeal.zealsay.common.constant.enums.Openness;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 角色返回对象.
 *
 * @author  zhanglei
 * @date 2018/11/15  7:13 PM
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {

  @ApiModelProperty(value = "id",example = "123")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;

  @ApiModelProperty(value = "标题",example = "震惊，男子半夜不回家竟然是在...")
  private String title;

  @ApiModelProperty(value = "副标题",example = "某男子半夜不回家竟然在办公室加班")
  private String subheading;

  @ApiModelProperty(value = "md格式内容",example = "男子加班成瘾，最后成为人生赢家")
  private String contentMd;

  @ApiModelProperty(value = "html格式内容",example = "男子加班成瘾，最后成为人生赢家")
  private String contentHtml;

  @ApiModelProperty(value = "封面图片")
  private String coverImage;

  @ApiModelProperty(value = "状态",example = "1")
  private ArticleStatus status;

  @ApiModelProperty(value = "公开度",example = "1")
  private Openness openness;

  @ApiModelProperty(value = "标签",example = "java,IT,云计算,大数据")
  private String label;

  @ApiModelProperty(value = "分类目录id",example = "1231")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long categoryId;

  @ApiModelProperty(value = "分类目录名称",example = "杂谈游记")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private String categoryName;

  @ApiModelProperty(value = "作者编号")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long authorId;

  @ApiModelProperty(value = "作者编号",example = "张三")
  private String authorName;

  @ApiModelProperty(value = "创建时间")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime createDate;

  @ApiModelProperty(value = "更新时间")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime updateDate;


}
