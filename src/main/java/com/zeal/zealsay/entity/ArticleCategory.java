package com.zeal.zealsay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

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
@ApiModel(value="ArticleCategory对象", description="分类目录表")
public class ArticleCategory {

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    private String name;

    private String alias;

    private String description;

    private Long parentId;


}
