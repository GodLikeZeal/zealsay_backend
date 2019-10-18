package com.zeal.zealsay.service.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zeal.zealsay.common.constant.enums.OauthSource;
import com.zeal.zealsay.common.constant.enums.Role;
import com.zeal.zealsay.common.constant.enums.UserStatus;
import com.zeal.zealsay.common.entity.SecuityUser;
import com.zeal.zealsay.entity.AuthUser;
import com.zeal.zealsay.entity.User;
import com.zeal.zealsay.service.AuthUserService;
import com.zeal.zealsay.service.UserService;
import com.zeal.zealsay.util.JwtTokenUtil;
import lombok.NonNull;
import me.zhyd.oauth.model.AuthResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录业务抽象类.
 *
 * @author zeal
 * @date 2019/9/11 21:04
 */
public abstract class AbstractOauthLogin implements OauthLogin {

  @Autowired
  AuthUserService authUserService;
  @Autowired
  UserDetailServiceImpl userDetailsService;
  @Autowired
  UserService userService;
  @Autowired
  JwtTokenUtil jwtTokenUtil;

  @Override
  public Map<String, Object> login(AuthResponse authResponse) {
    Map<String, Object> map = new HashMap<>();
    // 执行登录逻辑
    me.zhyd.oauth.model.AuthUser authUser = (me.zhyd.oauth.model.AuthUser) authResponse.getData();
    //判断是否注册
    //首先保存授权用户记录
    AuthUser user = toAuthUser(authUser);
    if (!isRegister(authUser.getUuid(), getSource())) {
      //没有注册，则注册成为用户
      authUserService.save(user);
      //检测用户名是否重复

      //保存入库
      userService.save(toUser(authUser));
    }
    //生成token并且登录
    SecuityUser secuityUser = userDetailsService
        .toSecuityUser(userService.getByAuthUser(authUser.getUuid(), getSource()));
    String token = jwtTokenUtil.generateToken(secuityUser);
    map.put("redirect", getRedirectUrl() + "?token=" + token);
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
        .username(checkAndSetUsername(authUser.getUsername(),1))
        .name(authUser.getNickname())
        .avatar(authUser.getAvatar())
        .address(authUser.getLocation())
        .status(UserStatus.NORMAL)
        .emailConfirm(false)
        .role(Role.USER)
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
