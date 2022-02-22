package com.zeal.zealsay.controller;

import cn.hutool.core.util.StrUtil;
import com.xkcoding.justauth.AuthRequestFactory;
import com.zeal.zealsay.common.constant.enums.OauthSource;
import com.zeal.zealsay.service.auth.OauthLoginFactory;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 第三方登录.
 *
 * @author zhanglei
 * @date 2019-09-11  14:26
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/oauth")
public class OauthController {

  private final AuthRequestFactory factory;

  @Autowired
  private OauthLoginFactory oauthLoginFactory;

  public OauthController(AuthRequestFactory factory) {
    this.factory = factory;
  }

  /**
   * 登录类型
   */
  @GetMapping
  public Map<String, String> loginType() {
    List<String> oauthList = factory.oauthList();
    return oauthList.stream().collect(Collectors.toMap(oauth -> oauth.toLowerCase() + "登录", oauth -> "http://oauth.xkcoding.com/demo/oauth/login/" + oauth.toLowerCase()));
  }

  /**
   * 登录
   *
   * @param oauthType 第三方登录类型
   * @param response  response
   * @throws IOException
   */
  @RequestMapping("/login/{oauthType}")
  public void renderAuth(@PathVariable String oauthType, HttpServletResponse response) throws IOException {
    AuthRequest authRequest = factory.get(getAuthSource(oauthType));
    response.sendRedirect(authRequest.authorize(oauthType + "::" + AuthStateUtils.createState()));
  }

  /**
   * 登录成功后的回调
   *
   * @param oauthType 第三方登录类型
   * @param callback  携带返回的信息
   * @return 登录成功后的信息
   */
  @RequestMapping("/{oauthType}/callback")
  public void login(@PathVariable String oauthType, AuthCallback callback,HttpServletResponse response) throws Exception {
    AuthRequest authRequest = factory.get(getAuthSource(oauthType));
    AuthResponse authResponse = authRequest.login(callback);
    log.info("【response】= {}", authRequest);
    //执行登录逻辑
    Map<String,Object> map = oauthLoginFactory.getOauthLogin(getOauthSource(oauthType)).login(authResponse);
    response.sendRedirect((String) map.get("redirect"));
  }

  private AuthSource getAuthSource(String type) {
    if (StrUtil.isNotBlank(type)) {
      return AuthSource.valueOf(type.toUpperCase());
    } else {
      throw new RuntimeException("不支持的类型");
    }
  }
  private OauthSource getOauthSource(String type) {
    if (StrUtil.isNotBlank(type)) {
      return OauthSource.valueOf(type.toUpperCase());
    } else {
      throw new RuntimeException("不支持的类型");
    }
  }
}

