package com.zeal.zealsay.dto.request;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 评论回复.
 *
 * @author zhanglei
 * @date 2020/6/16  4:41 下午
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommentRequest implements Serializable {


  /**
   * 评论内容.
   */
  @NotBlank(message = "评论内容不能为空")
  private String content;


  /**
   * 文章id.
   */
  @NotNull(message = "文章id不能为空")
  private Long articleId;

  /**
   * 文章名称.
   */
  @NotNull(message = "文章名称不能为空")
  private String articleName;

  /**
   * 父层id.
   */
  private Long commentId;


  /**
   * 评论者id.
   */
  @NotNull(message = "用户id不能为空")
  private Long fromId;

  /**
   * 评论者名称.
   */
  @NotNull(message = "用户名不能为空")
  private String fromName;


  /**
   * 头像.
   */
  @NotNull(message = "用户头像不能为空")
  private String fromAvatar;



}
