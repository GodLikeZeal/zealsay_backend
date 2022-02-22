package com.zeal.zealsay.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;

  private String name;

  private String alias;

  private String description;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long parentId;

  List<ArticleCategoryResponse> children;

}
