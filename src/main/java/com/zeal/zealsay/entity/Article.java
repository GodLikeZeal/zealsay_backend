package com.zeal.zealsay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.zeal.zealsay.common.constant.enums.ArticleStatus;
import com.zeal.zealsay.common.constant.enums.Openness;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

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
    private ArticleStatus status;


    /**
     * 公开度.
     */
    private Openness openness;

    /**
     * 标签.
     */
    private String label;

    /**
     * 阅读数.
     */
    private Integer readNum;

    /**
     * 点赞数.
     */
    private Integer likeNum;

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
    private LocalDateTime createDate;

    /**
     * 更新时间.
     */
    private LocalDateTime updateDate;


}
