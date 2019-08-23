package com.zeal.zealsay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 登录记录表
 * </p>
 *
 * @author zhanglei
 * @since 2019-03-16
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginLog {


    /**
     * 主键id.
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 用户id.
     */
    private Long userId;

    /**
     * 用户名称.
     */
    private String username;

    /**
     * 设备类型.
     */
    private String device;

    /**
     * 登录时间.
     */
    private LocalDateTime loginDate;

    /**
     * ip地址.
     */
    private String ip;

    /**
     * 国家.
     */
    private String country;

    /**
     * 省.
     */
    private String province;

    /**
     * 市.
     */
    private String city;

    /**
     * 区.
     */
    private String area;

    /**
     * 坐标.
     */
    private String coordinate;


}
