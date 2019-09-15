package com.zeal.zealsay.service.auth;

import com.google.common.collect.ImmutableList;
import com.zeal.zealsay.common.constant.enums.UserStatus;
import com.zeal.zealsay.common.entity.SecuityUser;
import com.zeal.zealsay.common.entity.UserVo;
import com.zeal.zealsay.entity.Role;
import com.zeal.zealsay.entity.User;
import com.zeal.zealsay.service.RoleService;
import com.zeal.zealsay.service.UserService;
import com.zeal.zealsay.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@Service(value = "userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

  @Value("${jwt.header}")
  private String tokenHeader;

  @Autowired
  UserService userService;
  @Autowired
  RoleService roleService;
  @Autowired
  JwtTokenUtil jwtTokenUtil;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    UserVo userVo = userService.userFind(s);
    if (userVo.getUser() != null) {
      return toSecuityUser(userVo.getUser());
    } else {
      throw new UsernameNotFoundException("该用户不存在");
    }
  }

  public String refresh(String oldToken) {
    final String token = oldToken.substring(tokenHeader.length());
    String username = jwtTokenUtil.getUsernameFromToken(token);
    SecuityUser user = (SecuityUser) loadUserByUsername(username);
    if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
      return jwtTokenUtil.refreshToken(token);
    }
    return null;
  }

  public SecuityUser getCurrentUser(){
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof SecuityUser) {
      return (SecuityUser)principal;
    }
    return null;
  }

  /**
   * 转换成SecuityUser.
   *
   * @author  zhanglei
   * @date 2019-09-12  15:12
   */
  public SecuityUser toSecuityUser(User user) {
    Collection<GrantedAuthority> grantedAuthorities = Collections
        .singleton(new SimpleGrantedAuthority(user.getRole().name()));
    return new SecuityUser(user.getLastPasswordResetDate(),
        user.getId(),
        user.getUsername(),
        user.getPassword(),
        user.getAvatar(),
        user.getSex(),
        user.getAge(),
        ImmutableList.of(user.getRole().name()),
        UserStatus.NORMAL.equals(user.getStatus()),
        true, true,
        !UserStatus.LOCK.equals(user.getStatus()),
        grantedAuthorities);
  }
}
