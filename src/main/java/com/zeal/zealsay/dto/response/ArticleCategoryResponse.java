package com.zeal.zealsay.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
public class ArticleCategoryResponse {

  @ApiModelProperty(value = "id",example = "12311")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;

  @ApiModelProperty(value = "名称",example = "奇淫技巧")
  private String name;

  @ApiModelProperty(value = "别名",example = "tec")
  private String alias;

  @ApiModelProperty(value = "说明",example = "记录技术分享")
  private String description;

  @ApiModelProperty(value = "父层id",example = "1")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long parentId;

  @ApiModelProperty(value = "子层目录")
  List<ArticleCategoryResponse> children;

}
