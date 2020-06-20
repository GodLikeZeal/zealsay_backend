//package com.zeal.zealsay.security.aop;
//
//import com.zeal.zealsay.common.biz.SpringBeanTool;
//import com.zeal.zealsay.common.constant.enums.ResultCode;
//import com.zeal.zealsay.common.entity.UserInfo;
//import com.zeal.zealsay.config.FilterIgnorePropertiesConfig;
//import com.zeal.zealsay.exception.ServiceException;
//import com.zeal.zealsay.security.core.TokenManager;
//import com.zeal.zealsay.service.auth.UserDetailServiceImpl;
//import com.zeal.zealsay.util.RedisUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//import static com.zeal.zealsay.security.core.TokenManager.MEMBER_TOKEN;
//
///**
// * token aop处理.
// *
// * @author zeal
// * @date 2020/1/12 21:09
// */
//@Slf4j
//@Aspect
//@Component("tokenAspect")
//public class TokenAspect {
//  @Autowired
//  private SpringBeanTool springBeanTool;
//  @Autowired
//  private RedisUtil redisUtils;
//  @Autowired
//  private TokenManager tokenManager;
//  @Autowired
//  private FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;
//  @Autowired
//  private UserDetailServiceImpl userDetailService;
//
//  // 配置织入点
//  @Pointcut("execution(* *..controller..*Controller*.*(..))")
//  public void pointCut() {
//  }
//
//  /**
//   * 拦截token
//   *
//   * @param joinPoint
//   * @throws IOException
//   */
//  @Before(value = "pointCut()")
//  public void doBefore(JoinPoint joinPoint) throws Exception {
//    handleLog(joinPoint);
//  }
//
//  private void handleLog(JoinPoint joinPoint) throws Exception {
//    HttpServletResponse response = springBeanTool.getResponse();
//    HttpServletRequest request = springBeanTool.getRequest();
//
//    if (passable(request)) {
//      //不需要校验
//      return;
//    }
//
//    String requestToken = request.getHeader("Authorization");
//    if (StringUtils.isBlank(requestToken)) {
//      requestToken = request.getHeader("authorization");
//      if (StringUtils.isBlank(requestToken)) {
//        // 可以使用以下方式返回json数据
//        // converter.write(JsonMsg.Error(Code.TOKEN_VALIDA_NULL),MediaType.APPLICATION_JSON, outputMessage);
//        // shutdownResponse(response);
//        // 不要用，用了这个在controller@responseBody无效，输出流关闭了
//        throw new ServiceException(ResultCode.TOKEN_VALIDA_NULL.getMessage());
//      }
//    }
//    Double score = redisUtils.score(MEMBER_TOKEN, requestToken);
//    if (score == null) {
//      // 终止继续往下面走，另外全局异常捕获AuthTokenException并给前端code码和提示
//      throw new ServiceException(ResultCode.TOKEN_VALIDA_FAIL.getMessage());
//    }
//
//    UserInfo user = (UserInfo) redisUtils.get(requestToken);
//
//    log.info("checking authentication for user '{}'", user.getUsername());
//    if (user.getUsername() != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//      log.info("security context was null, so authorizating user");
//
//      // It is not compelling necessary to load the use details from the database. You could also store the information
//      // in the token and read it from it. It's up to you ;)
//      UserDetails userDetails = userDetailService.loadUserByUsername(user.getUsername());
//
//      // For simple validation it is completely sufficient to just check the token integrity. You don't have to call
//      // the database compellingly. Again it's up to you ;)
//
//      UsernamePasswordAuthenticationToken authentication =
//          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//      log.info("authorizated user '{}', setting security context", user.getUsername());
//      SecurityContextHolder.getContext().setAuthentication(authentication);
//
//    }
//
//    // 获取redis已有的member信息，不查数据库,重新生成token放入
////        UserInfo user = (UserInfo) redisUtils.get(requestToken);
//    // 移除之前的token（包含member信息、token排行信息）
////        tokenManager.delToken(user);
////        String saveToken = tokenManager.saveToken(user);
////        response.setHeader("Access-Control-Expose-Headers",
////                "Cache-Control,Content-Type,Expires,Pragma,Content-Language,Last-Modified,token");
////        response.setHeader("token", saveToken); // 设置响应头
//  }
//
//  private void shutdownResponse(HttpServletResponse response) throws IOException {
//    response.getOutputStream().close();
//  }
//
//  /**
//   * 判断是否是白名单.
//   *
//   * @author zeal
//   * @date 2020/1/12 22:26
//   */
//  private boolean passable(HttpServletRequest request) {
//    AntPathMatcher pathMatcher = new AntPathMatcher();
//    for (String url : filterIgnorePropertiesConfig.getUrls()) {
//      if (pathMatcher.match(url, request.getRequestURI())) {
//        log.info("this path authentication unnecessary!");
//        return true;
//      }
//    }
//    return false;
//  }
//
//}
