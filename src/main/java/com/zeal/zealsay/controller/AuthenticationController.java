
package com.zeal.zealsay.controller;

import com.google.common.collect.ImmutableMap;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.common.entity.SecuityUser;
import com.zeal.zealsay.service.auth.UserDetailServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 认证登录相关controller.
 *
 * @author zhanglei
 * @date 2018/9/27  下午3:47
 */
@Api(tags = "认证模块")
@Slf4j
@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

  @Value("${jwt.header}")
  private String tokenHeader;

  @Autowired
  UserDetailServiceImpl userDetailService;

  /**
   * 认证页面
   *
   * @return ModelAndView
   */
  @GetMapping("/require")
  @ApiOperation(value = "认证页面跳转",notes = "认证页面跳转")
  public ModelAndView require() {
    return new ModelAndView("/ftl/login.ftl");
  }

  /**
   * 用户信息校验
   *
   * @param authentication 信息
   * @return 用户信息
   */
  @PostAuthorize(" returnObject.data.username == principal.username or hasRole('ADMIN')")
  @GetMapping("/user")
  @ApiOperation(value = "用户信息校验",notes = "用户信息校验")
  public Result<SecuityUser> user(Authentication authentication) {
    return Result.of(authentication.getPrincipal());
  }

  /**
   * 刷新token
   *
   * @return 用户信息
   */
  @PreAuthorize("hasAnyRole('USER','ADMIN')")
  @RequestMapping(value = "/refresh",method = {RequestMethod.GET,RequestMethod.POST})
  @ApiOperation(value = "刷新token",notes = "刷新token")
  public Result refreshAndGetAuthenticationToken(HttpServletRequest request)
      throws AuthenticationException {
    String token = request.getHeader(tokenHeader);
    String refreshedToken = userDetailService.refresh(token);
    if (refreshedToken == null) {
      return Result.serverError();
    } else {
      return Result.of(ImmutableMap
          .of("code","ok","message","刷新token成功","token", token));
    }
  }
}