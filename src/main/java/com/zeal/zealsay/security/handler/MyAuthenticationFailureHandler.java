package com.zeal.zealsay.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeal.zealsay.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.zeal.zealsay.common.constant.enums.ResultCode.*;

/**
 * 登录失败逻辑.
 *
 * @author zhanglei
 * @date 2018/10/24  4:49 PM
 */
@Slf4j
@Component("myAuthenticationFailureHandler")
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                      HttpServletResponse httpServletResponse,
                                      AuthenticationException e) throws IOException, ServletException {
    httpServletResponse.setContentType("application/json;charset=UTF-8");
    httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    if (e instanceof BadCredentialsException) {
      httpServletResponse.getWriter()
          .write(objectMapper
              .writeValueAsString(Result.builder()
                  .code(BAD_CREDENTIALS.getCode())
                  .message(BAD_CREDENTIALS.getMessage())
                  .build()));
    }
    if (e instanceof UsernameNotFoundException) {
      httpServletResponse.getWriter()
          .write(objectMapper
              .writeValueAsString(Result.builder()
                  .code(USERNAME_NOTFOUND.getCode())
                  .message(USERNAME_NOTFOUND.getMessage())
                  .build()));
    }
    if (e instanceof LockedException) {
      httpServletResponse.getWriter()
          .write(objectMapper
              .writeValueAsString(Result.builder()
                  .code(ACCOUNT_LOCKED.getCode())
                  .message(ACCOUNT_LOCKED.getMessage())
                  .build()));
    }
    if (e instanceof NonceExpiredException) {
      httpServletResponse.getWriter()
          .write(objectMapper
              .writeValueAsString(Result.builder()
                  .code(NONCE_EXPIRED.getCode())
                  .message(NONCE_EXPIRED.getMessage())
                  .build()));
    }
    if (e instanceof DisabledException) {
      httpServletResponse.getWriter()
          .write(objectMapper
              .writeValueAsString(Result.builder()
                  .code(ACCOUNT_DISABLED.getCode())
                  .message(ACCOUNT_DISABLED.getMessage())
                  .build()));
    }
  }
}
