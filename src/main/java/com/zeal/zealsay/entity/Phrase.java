package com.zeal.zealsay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhanglei
 * @since 2019-06-30
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Phrase {

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 正文
     */
    private String hitokoto;

    /**
     * 类型
     */
    private String type;

    /**
     * 来源
     */
    private String source;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    private LocalDate createdAt;


}
