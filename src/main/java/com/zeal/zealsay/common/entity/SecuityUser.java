package com.zeal.zealsay.common.entity;

import com.zeal.zealsay.common.constant.enums.Role;
import com.zeal.zealsay.common.constant.enums.UserStatus;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;


@Data
public class SecuityUser extends User {

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

//  @Builder
//  public SecuityUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,Long id, String name, Integer sex, Integer age, String introduction, String label, String phoneNumber, String email, Boolean emailConfirm, String avatar, String address, String province, String city, String area, Role role, Long deptId, UserStatus status, LocalDateTime registerDate, Date lastPasswordResetDate, LocalDateTime updateDate) {
//    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//    this.userId = id;
//    this.name = name;
//    this.sex = sex;
//    this.age = age;
//    this.introduction = introduction;
//    this.label = label;
//    this.phoneNumber = phoneNumber;
//    this.email = email;
//    this.emailConfirm = emailConfirm;
//    this.avatar = avatar;
//    this.address = address;
//    this.province = province;
//    this.city = city;
//    this.area = area;
//    this.role = role;
//    this.deptId = deptId;
//    this.status = status;
//    this.registerDate = registerDate;
//    this.lastPasswordResetDate = lastPasswordResetDate;
//  }

//  public SecuityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
//    super(username, password, authorities);
//  }
//
//  public SecuityUser(Long userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
//    super(username, password, authorities);
//    this.userId = userId;
//  }
//
//  public SecuityUser(Long userId, String username, String password, List<String> roles, Collection<? extends GrantedAuthority> authorities) {
//    super(username, password, authorities);
//    this.userId = userId;
//    this.roles = roles;
//  }
//
//  public SecuityUser(Date lastPasswordResetDate, Long userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
//    super(username, password, authorities);
//    this.userId = userId;
//    this.lastPasswordResetDate = lastPasswordResetDate;
//  }
//
//  public SecuityUser(Date lastPasswordResetDate, Long userId, String username, String password, List<String> roles, Collection<? extends GrantedAuthority> authorities) {
//    super(username, password, authorities);
//    this.userId = userId;
//    this.lastPasswordResetDate = lastPasswordResetDate;
//    this.roles = roles;
//  }
//
//  public SecuityUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
//    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//  }
//
//  public SecuityUser(Long userId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
//    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//    this.userId = userId;
//  }
//
//  public SecuityUser(Long userId, String username, String password, List<String> roles, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
//    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//    this.userId = userId;
//    this.roles = roles;
//  }
//
//  public SecuityUser(Date lastPasswordResetDate, Long userId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
//    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//    this.userId = userId;
//    this.lastPasswordResetDate = lastPasswordResetDate;
//  }
//
//  public SecuityUser(Date lastPasswordResetDate, Long userId, String username, String password, List<String> roles, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
//    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//    this.userId = userId;
//    this.lastPasswordResetDate = lastPasswordResetDate;
//    this.roles = roles;
//  }
//
//  public SecuityUser(Date lastPasswordResetDate, Long userId, String username, String password, String avatar, Integer sex, Integer age, List<String> roles, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
//    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//    this.userId = userId;
//    this.lastPasswordResetDate = lastPasswordResetDate;
//    this.roles = roles;
//    this.avatar = avatar;
//    this.sex = sex;
//    this.age = age;
//  }
}
