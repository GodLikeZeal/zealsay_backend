package com.zeal.zealsay.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class ArticleCategoryUpdateRequest {

  private Long id;

  private String name;

  private String alias;

  private String description;

  private Long parentId;

}
