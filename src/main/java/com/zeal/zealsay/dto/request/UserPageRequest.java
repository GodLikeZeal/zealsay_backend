package com.zeal.zealsay.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.zeal.zealsay.common.constant.enums.Role;
import io.swagger.annotations.ApiModelProperty;
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

  @ApiModelProperty(value = "id", example = "123")
  private Long id;

  @ApiModelProperty(value = "真实名称", example = "张三")
  private String name;

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

  @ApiModelProperty(value = "市", example = "110100")
  private Integer city;

  @ApiModelProperty(value = "区", example = "110101")
  private Integer area;

  @ApiModelProperty(value = "角色", example = "USER")
  private Role role;

  @ApiModelProperty(value = "部门id",example = "1")
  private Long deptId;

  @ApiModelProperty(value = "注册时间")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDateTime registerDate;

  @ApiModelProperty(value = "最后修改密码时间")
  private Date lastPasswordResetDate;

}
