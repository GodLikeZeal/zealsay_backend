package com.zeal.zealsay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class Comment implements Serializable {

  /**
   * id.
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private Long id;


  /**
   * 评论内容.
   */
  private String content;


  /**
   * 文章id.
   */
  private Long articleId;

  /**
   * 文章名称.
   */
  private String articleName;


  /**
   * 父层id.
   */
  private Long commentId;


  /**
   * 评论者id.
   */
  private Long fromId;

  /**
   * 评论者名称.
   */
  private String fromName;


  /**
   * 头像.
   */
  private String fromAvatar;


  /**
   * 点赞人数.
   */
  private Integer likeNum;

  /**
   * 是否删除.
   */
  @TableLogic
  private Boolean isDel;

  /**
   * 创建时间.
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime createDate;


}
