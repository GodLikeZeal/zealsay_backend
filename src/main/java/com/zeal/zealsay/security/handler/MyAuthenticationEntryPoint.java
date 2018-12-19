package com.zeal.zealsay.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeal.zealsay.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.zeal.zealsay.common.constant.enums.ResultCode.FORBIDDEN;

/**
 * 自定义403返回.
 *
 * @author zhanglei
 * @date 2018/10/24  4:48 PM
 */
@Slf4j
@Component("myAuthenticationEntryPoint")
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void commence(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AuthenticationException e) throws IOException, ServletException {

    httpServletResponse.setContentType("application/json;charset=UTF-8");
//    httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
    httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    httpServletResponse.getWriter()
        .write(objectMapper
            .writeValueAsString(Result.builder()
                .code(FORBIDDEN.getCode())
                .message(FORBIDDEN.getMessage())
                .build()));
  }
}
