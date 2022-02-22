package com.zeal.zealsay.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zeal.zealsay.common.constant.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户列表请求参数.
 *
 * @author  zhanglei
 * @date 2018/11/15  9:40 PM
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPageRequest {

  private Long id;

  private String name;

  private Integer sex;

  private Integer age;

  private String phoneNumber;

  private String email;

  private String avatar;

  private String address;

  private Integer province;

  private Integer city;

  private Integer area;

  private Role role;

  private Long deptId;

  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDateTime registerDate;

  private Date lastPasswordResetDate;

}
