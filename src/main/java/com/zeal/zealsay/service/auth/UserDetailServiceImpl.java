package com.zeal.zealsay.service.auth;

import com.google.common.collect.ImmutableList;
import com.zeal.zealsay.common.constant.enums.UserStatus;
import com.zeal.zealsay.common.entity.SecuityUser;
import com.zeal.zealsay.common.entity.UserVo;
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
  JwtTokenUtil jwtTokenUtil;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    UserVo userVo = userService.userFind(s);
    if (userVo.getUser() != null) {
      Collection<GrantedAuthority> grantedAuthorities = Collections
          .singleton(new SimpleGrantedAuthority(userVo.getRole().getValue()));
      return new SecuityUser(userVo.getUser().getLastPasswordResetDate(),
          userVo.getUser().getId(),
          userVo.getUser().getUsername(),
          userVo.getUser().getPassword(),
          userVo.getUser().getAvatar(),
          userVo.getUser().getSex(),
          userVo.getUser().getAge(),
          ImmutableList.of(userVo.getRole().getValue()),
           UserStatus.NORMAL.equals(userVo.getUser().getStatus()),
          true, true,
          !UserStatus.LOCK.equals(userVo.getUser().getStatus()),
          grantedAuthorities);
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
}
