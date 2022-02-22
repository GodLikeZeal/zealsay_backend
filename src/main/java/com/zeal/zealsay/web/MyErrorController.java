package com.zeal.zealsay.web;

import com.zeal.zealsay.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.zeal.zealsay.common.constant.enums.ResultCode.*;


/**
 * @author zeal
 * @version 1.0.0
 * @description 处理自定义Error的Controller
 * @date 2018-05-09 20:38
 */
@Slf4j
@RestController
public class MyErrorController implements ErrorController {

  private static final String ERROR_PATH = "/error";

  @RequestMapping(value = ERROR_PATH)
  public Result<?> handleError(HttpServletRequest request) {
    //获取statusCode:401,404,500
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    switch (statusCode) {
      case 400:
        return Result.builder()
            .code(FAILED.getCode())
            .message(FAILED.getMessage())
            .build();
      case 401:
        return Result.builder()
            .code(ACCESS_DENIED.getCode())
            .message(ACCESS_DENIED.getMessage())
            .build();
      case 403:
        return Result.builder()
            .code(FORBIDDEN.getCode())
            .message(FORBIDDEN.getMessage())
            .build();
      case 404:
        return Result.builder()
            .code(NOT_FOUND.getCode())
            .message(NOT_FOUND.getMessage())
            .build();
      case 500:
        return Result.builder()
            .code(INTERNAL_SERVER_ERROR.getCode())
            .message(INTERNAL_SERVER_ERROR.getMessage())
            .build();
      case 502:
        return Result.builder()
            .code(BAD_GATEWAY.getCode())
            .message(BAD_GATEWAY.getMessage())
            .build();
      default:
        return Result.builder()
            .code(INTERNAL_SERVER_ERROR.getCode())
            .message(INTERNAL_SERVER_ERROR.getMessage())
            .build();
    }

  }

}
