package com.zeal.zealsay.service.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zeal.zealsay.common.constant.SystemConstants;
import com.zeal.zealsay.common.constant.enums.OauthSource;
import com.zeal.zealsay.common.constant.enums.Role;
import com.zeal.zealsay.common.constant.enums.UserStatus;
import com.zeal.zealsay.common.entity.SecuityUser;
import com.zeal.zealsay.entity.AuthUser;
import com.zeal.zealsay.entity.User;
import com.zeal.zealsay.helper.UserHelper;
import com.zeal.zealsay.security.core.TokenManager;
import com.zeal.zealsay.service.AuthUserService;
import com.zeal.zealsay.service.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 登录业务抽象类.
 *
 * @author zeal
 * @date 2019/9/11 21:04
 */
@Slf4j
public abstract class AbstractOauthLogin implements OauthLogin {

  @Autowired
  AuthUserService authUserService;
  @Autowired
  UserDetailServiceImpl userDetailsService;
  @Autowired
  UserService userService;
  @Autowired
  TokenManager tokenManager;
  @Autowired
  UserHelper userHelper;
  @Autowired
  SystemConstants systemConstants;

  @Override
  public Map<String, Object> login(AuthResponse authResponse) {
    Map<String, Object> map = new HashMap<>();
    // 执行登录逻辑
    me.zhyd.oauth.model.AuthUser authUser = (me.zhyd.oauth.model.AuthUser) authResponse.getData();
    //判断是否注册
    //首先保存授权用户记录
    AuthUser user = toAuthUser(authUser);
    SecuityUser secuityUser;
    User u;
    if (isRegister(authUser.getUuid(), getSource())) {
      u = userService.getByAuthUser(authUser.getUuid(), getSource());
      secuityUser = userDetailsService.toSecuityUser(u);
    } else {
      log.info("第一次授权，执行注册逻辑");
      u = toUser(authUser);
      //保存入库
      userService.save(u);
      //没有注册，则注册成为用户
      user.setUserId(u.getId());
      authUserService.save(user);
      //封装登录对象
      u.setLastPasswordResetDate(new Date());
      u.setAvatar("");
      u.setSex(1);
      u.setAge(18);
      secuityUser = userDetailsService.toSecuityUser(u);
    }
    //生成token并且登录
    String token = null;
    try {
      token = URLEncoder.encode(tokenManager.generateToken(secuityUser),"utf-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    map.put("redirect", getRedirectUrl() + "?token=" + token);
    log.info("token为:{}", map);
    return map;
  }

  protected abstract String getRedirectUrl();

  protected abstract OauthSource getSource();

  /**
   * 是否注册过.
   *
   * @return
   */
  private boolean isRegister(@NonNull String uid, OauthSource source) {
    List<AuthUser> authUsers = authUserService
        .list(new QueryWrapper<AuthUser>()
            .eq("uid", uid)
            .eq("source", source));
    return !CollectionUtils.isEmpty(authUsers);
  }

  /**
   * 构建Auth实例.
   *
   * @param authUser
   * @return
   */
  private AuthUser toAuthUser(me.zhyd.oauth.model.AuthUser authUser) {
    return AuthUser.builder()
        .uid(authUser.getUuid())
        .username(authUser.getUsername())
        .nickname(authUser.getNickname())
        .avatar(authUser.getAvatar())
        .blog(authUser.getBlog())
        .company(authUser.getCompany())
        .location(authUser.getLocation())
        .email(authUser.getEmail())
        .remark(authUser.getRemark())
        .gender(authUser.getGender().name())
        .bind(true)
        .source(authUser.getSource().name())
        .build();
  }

  /**
   * 构建注册用户实例.
   *
   * @param authUser
   * @return
   */
  private User toUser(me.zhyd.oauth.model.AuthUser authUser) {
    return User.builder()
        .username(checkAndSetUsername(authUser.getUsername(), 1))
        .password(systemConstants.getDefaultPassword())
        .name(authUser.getNickname())
        .avatar(StringUtils.isNotBlank(authUser.getAvatar()) ? authUser.getAvatar() : userHelper.gennerateAvatar())
        .address(authUser.getLocation())
        .status(UserStatus.NORMAL)
        .emailConfirm(false)
        .role(Role.ROLE_USER)
        .introduction("这人懒死了，什么都没有写⊙﹏⊙∥∣°")
        .registerDate(LocalDateTime.now())
        .build();
  }

  /**
   * 校验用户名，重复则加1尝试.
   *
   * @author zhanglei
   * @date 2019-10-18  18:48
   */
  private String checkAndSetUsername(String username, Integer i) {
    if (userService.getIsInUseByUsername(username)) {
      return checkAndSetUsername(username + "_" + i.toString(), i++);
    } else {
      return username;
    }
  }


}
