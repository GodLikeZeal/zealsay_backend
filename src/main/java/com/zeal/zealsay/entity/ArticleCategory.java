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
 * @since 2018-12-29
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ArticleCategory implements Serializable {

    /**
     * 主键id.
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 名称.
     */
    private String name;

    /**
     * 别名.
     */
    private String alias;

    /**
     * 描述.
     */
    private String description;

    /**
     * 父类id.
     */
    private Long parentId;


}
