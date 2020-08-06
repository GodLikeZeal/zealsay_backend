package com.zeal.zealsay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 全局字典表
 * </p>
 *
 * @author zhanglei
 * @since 2019-03-27
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_dict")
@Accessors(chain = true)
public class Dict implements Serializable {

  /**
   * 主键.
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private Long id;

  /**
   * 编码.
   */
  private Integer code;

  /**
   * 名称.
   */
  private String name;

  /**
   * 拼音简称.
   */
  private String enShort;

  /**
   * 父层code.
   */
  private Integer parentCode;

  /**
   * 类型.
   */
  private String type;

  /**
   * 描述.
   */
  private String description;

  /**
   * 顺序.
   */
  private Integer sort;


}
