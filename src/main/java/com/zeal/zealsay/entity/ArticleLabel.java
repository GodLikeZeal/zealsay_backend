package com.zeal.zealsay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 分类目录表
 * </p>
 *
 * @author zhanglei
 * @since 2019-05-16
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ArticleLabel implements Serializable {

  /**
   * id.
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private Long id;

  /**
   * 名称.
   */
  private String name;

  /**
   * 图标.
   */
  private String icon;

  /**
   * 热度.
   */
  private Long hot;

  /**
   * 外层颜色.
   */
  private String outColor;

  /**
   * 头像颜色.
   */
  private String avatarColor;


}
