package com.zeal.zealsay.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.zeal.zealsay.common.constant.enums.Role;
import com.zeal.zealsay.common.constant.enums.UserStatus;
import lombok.AllArgsConstructor;
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

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;

  private String username;

  private String name;

  private String password;

  private String introduction;

  private String label;

  private Integer sex;

  private Integer age;

  private String phoneNumber;

  private String email;

  private String avatar;

  private String address;

  private Integer province;

  private String provinceName;

  private Integer city;

  private String cityName;

  private Integer area;

  private String areaName;

  private Role role;

  private Long deptId;

  private UserStatus status;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime registerAt;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @JsonSerialize(using = DateSerializer.class)
  private Date lastPasswordResetDate;

}
