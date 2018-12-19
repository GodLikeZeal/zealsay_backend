package com.zeal.zealsay.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.common.entity.SecuityUser;
import com.zeal.zealsay.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功并返回token逻辑.
 *
 * @author  zhanglei
 * @date 2018/10/24  4:58 PM
 */
@Slf4j
@Component("myLogoutSuccessHandler")
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  JwtTokenUtil jwtTokenUtil;


  @Override
  public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
    httpServletResponse.setContentType("application/json;charset=UTF-8");
    httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    httpServletResponse.getWriter()
        .write(objectMapper
            .writeValueAsString(Result.ok()));
  }
}
