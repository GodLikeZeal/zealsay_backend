package com.zeal.zealsay.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.zeal.zealsay.common.constant.enums.Role;
import com.zeal.zealsay.common.constant.enums.UserStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户返回对象.
 *
 * @author  zhanglei
 * @date 2018/11/15  6:39 PM
 */
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

  @ApiModelProperty(value = "id", example = "123")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;

  @ApiModelProperty(value = "用户名", example = "浪里小白龙")
  private String username;

  @ApiModelProperty(value = "真实名称", example = "张三")
  private String name;

  @ApiModelProperty(value = "密码", example = "12345")
  private String password;

  @ApiModelProperty(value = "简介", example = "一位低调的足球爱好者")
  private String introduction;

  @ApiModelProperty(value = "标签", example = "宅男,潜力股")
  private String label;

  @ApiModelProperty(value = "性别", example = "1")
  private Integer sex;

  @ApiModelProperty(value = "年龄", example = "24")
  private Integer age;

  @ApiModelProperty(value = "手机号", example = "13322345678")
  private String phoneNumber;

  @ApiModelProperty(value = "邮箱", example = "13342345678@qq.com")
  private String email;

  @ApiModelProperty(value = "头像")
  private String avatar;

  @ApiModelProperty(value = "地址", example = "北京市朝阳中央大街12号")
  private String address;

  @ApiModelProperty(value = "省", example = "110000")
  private Integer province;

  @ApiModelProperty(value = "省", example = "北京市")
  private String provinceName;

  @ApiModelProperty(value = "市", example = "110100")
  private Integer city;

  @ApiModelProperty(value = "市", example = "北京市")
  private String cityName;

  @ApiModelProperty(value = "区", example = "110104")
  private Integer area;

  @ApiModelProperty(value = "区", example = "朝阳区")
  private String areaName;

  @ApiModelProperty(value = "角色", example = "USER")
  private Role role;

  @ApiModelProperty(value = "部门id")
  private Long deptId;

  @ApiModelProperty(value = "状态", example = "NORMAL")
  private UserStatus status;

  @ApiModelProperty(value = "注册时间", example = "2018.9.26 12:12:12")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime registerAt;

  @ApiModelProperty(value = "最后密码修改时间", example = "2018.9.26 12:12:12")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @JsonSerialize(using = DateSerializer.class)
  private Date lastPasswordResetDate;

}
