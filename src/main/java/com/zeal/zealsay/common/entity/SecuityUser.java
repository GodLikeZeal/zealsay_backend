package com.zeal.zealsay.common.entity;

import com.zeal.zealsay.common.constant.enums.Role;
import com.zeal.zealsay.common.constant.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;


@Getter
@Setter
public class SecuityUser extends User  implements Serializable {

  /**
   * id.
   */
  private Long userId;

  /**
   * 真实名称.
   */
  private String name;


  /**
   * 性别.
   */
  private Integer sex;

  /**
   * 年龄.
   */
  private Integer age;

  /**
   * 简介.
   */
  private String introduction;

  /**
   * 标签.
   */
  private String label;

  /**
   * 手机号.
   */
  private String phoneNumber;

  /**
   * 邮箱.
   */
  private String email;

  /**
   * 邮箱是否验证过.
   */
  private Boolean emailConfirm;

  /**
   * 头像.
   */
  private String avatar;

  /**
   * 地址.
   */
  private String address;

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
   * 角色.
   */
  private Role role;

  /**
   * 部门id.
   */
  private Long deptId;

  /**
   * 状态.
   */
  private UserStatus status;

  /**
   * 注册时间.
   */
  private LocalDateTime registerDate;

  /**
   * 最后密码修改时间.
   */
  private Date lastPasswordResetDate;


  public SecuityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }

  public SecuityUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
  }

}
