package com.zeal.zealsay.dto.request;

import com.zeal.zealsay.common.constant.enums.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 用户修改对象.
 *
 * @author  zhanglei
 * @date 2018/11/15  7:28 PM
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

  @ApiModelProperty(value = "id", example = "123")
  private Long id;

  @ApiModelProperty(value = "用户名", example = "zhangsan")
  private String username;

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

  @ApiModelProperty(value = "省", example = "北京市")
  private String province;

  @ApiModelProperty(value = "市", example = "北京市")
  private String city;

  @ApiModelProperty(value = "区", example = "朝阳区")
  private String area;

  @ApiModelProperty(value = "角色", example = "USER")
  private Role role;

  @ApiModelProperty(value = "部门id",example = "1")
  private Long deptId;

}
