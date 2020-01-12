package com.zeal.zealsay.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.common.entity.SecuityUser;
import com.zeal.zealsay.security.core.TokenManager;
import com.zeal.zealsay.service.LoginLogService;
import com.zeal.zealsay.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.zeal.zealsay.common.constant.enums.ResultCode.OK;

/**
 * 登录成功并返回token逻辑.
 *
 * @author  zhanglei
 * @date 2018/10/24  4:58 PM
 */
@Slf4j
@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  JwtTokenUtil jwtTokenUtil;

  @Autowired
  LoginLogService loginLogService;

  @Autowired
  TokenManager tokenManager;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                      HttpServletResponse httpServletResponse,
                                      Authentication authentication) throws IOException {
    httpServletResponse.setContentType("application/json;charset=UTF-8");
    httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    final SecuityUser secuityUser = (SecuityUser) authentication.getPrincipal();
    String token = null;
    try {
      token = tokenManager.saveToken(secuityUser);
    } catch (Exception e) {
      e.printStackTrace();
    }

//    final String token = jwtTokenUtil.generateToken(secuityUser);
    log.info("----------------用户'{}'登录成功，开始执行初始化-----------------------",secuityUser.getUsername());
    httpServletResponse.getWriter()
        .write(objectMapper
            .writeValueAsString(Result.builder()
                .code(OK.getCode())
                .message(OK.getMessage())
                .data(ImmutableMap.of("token", token))
                .build()));
    //记录登录信息
    loginLogService.saveLog(httpServletRequest,secuityUser);
  }
}
