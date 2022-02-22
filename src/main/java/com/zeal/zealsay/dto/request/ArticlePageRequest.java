package com.zeal.zealsay.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zeal.zealsay.common.constant.enums.ArticleStatus;
import com.zeal.zealsay.common.constant.enums.Openness;
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

  private String title;

  private String subheading;

  private String authorName;

  private String authorPhone;

  private ArticleStatus status;

  private Openness openness;

  private String label;

  private Long categoryId;

  private Long authorId;

  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate endDate;

}
