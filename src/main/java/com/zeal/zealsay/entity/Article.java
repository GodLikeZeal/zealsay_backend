package com.zeal.zealsay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author zhanglei
 * @since 2018-11-28
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Article {

    /**
     * id.
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;


    /**
     * 标题.
     */
    private String title;


    /**
     * 副标题.
     */
    private String subheading;


    /**
     * markdown内容.
     */
    private String contentMd;


    /**
     * html内容.
     */
    private String contentHtml;

    /**
     * 封面图片.
     */
    private String coverImage;


    /**
     * 状态.
     */
    private Integer status;


    /**
     * 公开度.
     */
    private Integer openness;

    /**
     * 标签.
     */
    private String label;

    /**
     * 分类目录id.
     */
    private Long categoryId;

    /**
     * 作者编号.
     */
    private Long authorId;

    /**
     * 是否删除.
     */
    @TableLogic
    private Boolean isDel;

    /**
     * 创建时间.
     */
    private LocalDateTime createAt;

    /**
     * 更新时间.
     */
    private LocalDateTime updateAt;


}
