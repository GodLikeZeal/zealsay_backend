package com.zeal.zealsay.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 全局字典表
 * </p>
 *
 * @author zhanglei
 * @since 2019-03-27
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dict")
@ApiModel(value="Dict对象", description="全局字典表")
public class Dict {

    /**
     * 主键.
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
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
