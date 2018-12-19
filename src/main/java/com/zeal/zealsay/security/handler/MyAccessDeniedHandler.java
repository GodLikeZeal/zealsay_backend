package com.zeal.zealsay.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeal.zealsay.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.zeal.zealsay.common.constant.enums.ResultCode.ACCESS_DENIED;

/**
 * 自定义401无权限返回.
 *
 * @author zhanglei
 * @date 2018/10/24  4:48 PM
 */
@Slf4j
@Component("myAccessDeniedHandler")
public class MyAccessDeniedHandler implements AccessDeniedHandler {

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void handle(HttpServletRequest httpServletRequest,
                     HttpServletResponse httpServletResponse,
                     AccessDeniedException e) throws IOException, ServletException {
    httpServletResponse.setContentType("application/json;charset=UTF-8");
//    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    if (e instanceof AccessDeniedException) {
      httpServletResponse.getWriter()
          .write(objectMapper
              .writeValueAsString(Result.builder()
                  .code(ACCESS_DENIED.getCode())
                  .message(ACCESS_DENIED.getMessage())
                  .build()));
    }
  }
}
