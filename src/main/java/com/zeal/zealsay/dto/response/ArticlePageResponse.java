package com.zeal.zealsay.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.zeal.zealsay.common.constant.enums.ArticleStatus;
import com.zeal.zealsay.common.constant.enums.Openness;
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
public class ArticlePageResponse {

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;

  private String title;

  private String subheading;

  private String coverImage;

  private ArticleStatus status;

  private Openness openness;

  private String label;

  private Integer readNum;

  private Integer likeNum;

  private Integer commentNum;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long categoryId;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private String categoryName;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long authorId;

  private String authorName;

  private String authorAvatar;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime createDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime updateDate;


}
