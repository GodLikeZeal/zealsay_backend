package com.zeal.zealsay.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标签云添加返回对象.
 *
 * @author  zhanglei
 * @date 2018/12/29  3:23 PM
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleLabelResponse {

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;

  private String name;

  private String icon;

  private Long hot;

  private String outColor;

  private String avatarColor;

}
