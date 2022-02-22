package com.zeal.zealsay.dto.request;

import com.zeal.zealsay.common.constant.enums.ArticleStatus;
import com.zeal.zealsay.common.constant.enums.Openness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 文章更新请求对象.
 *
 * @author  zhanglei
 * @date 2018/12/29  3:23 PM
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdateRequest {

  @NotEmpty(message = "主键不能为空")
  private Long id;

  private String title;

  private String subheading;

  private String contentMd;

  private String contentHtml;

  private String coverImage;

  private ArticleStatus status;

  private Openness openness;

  private List<String> label;

  private Long categoryId;

}
