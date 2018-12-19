package com.zeal.zealsay.common.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SecuityUser extends User {

  private Long userId;

  private String avatar;

  private Integer sex;

  private Integer age;

  private Date lastPasswordResetDate;

  private List<String> roles;

  public SecuityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }

  public SecuityUser(Long userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.userId = userId;
  }

  public SecuityUser(Long userId, String username, String password, List<String> roles, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.userId = userId;
    this.roles = roles;
  }

  public SecuityUser(Date lastPasswordResetDate, Long userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.userId = userId;
    this.lastPasswordResetDate = lastPasswordResetDate;
  }

  public SecuityUser(Date lastPasswordResetDate, Long userId, String username, String password, List<String> roles, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.userId = userId;
    this.lastPasswordResetDate = lastPasswordResetDate;
    this.roles = roles;
  }

  public SecuityUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
  }

  public SecuityUser(Long userId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    this.userId = userId;
  }

  public SecuityUser(Long userId, String username, String password, List<String> roles, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    this.userId = userId;
    this.roles = roles;
  }

  public SecuityUser(Date lastPasswordResetDate, Long userId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    this.userId = userId;
    this.lastPasswordResetDate = lastPasswordResetDate;
  }

  public SecuityUser(Date lastPasswordResetDate, Long userId, String username, String password, List<String> roles, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    this.userId = userId;
    this.lastPasswordResetDate = lastPasswordResetDate;
    this.roles = roles;
  }

  public SecuityUser(Date lastPasswordResetDate, Long userId, String username, String password, String avatar, Integer sex, Integer age, List<String> roles, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    this.userId = userId;
    this.lastPasswordResetDate = lastPasswordResetDate;
    this.roles = roles;
    this.avatar = avatar;
    this.sex = sex;
    this.age = age;
  }
}
