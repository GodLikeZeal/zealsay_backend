package com.zeal.zealsay.security.filter;

import com.zeal.zealsay.common.entity.UserInfo;
import com.zeal.zealsay.config.FilterIgnorePropertiesConfig;
import com.zeal.zealsay.security.core.TokenManager;
import com.zeal.zealsay.service.auth.UserDetailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
public class AuthorizationTokenFilter extends OncePerRequestFilter {

  @Autowired
  FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;

  private final UserDetailServiceImpl userDetailsService;
  private final String tokenHeader;
  @Autowired
  TokenManager tokenManager;

  public AuthorizationTokenFilter(UserDetailServiceImpl userDetailsService,
                                  @Value("${jwt.header}") String tokenHeader) {
    this.userDetailsService = userDetailsService;
    this.tokenHeader = tokenHeader;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    log.info("processing authentication for '{}'", request.getRequestURL());
    if (!passable(request)) {
      final String requestHeader = request.getHeader(this.tokenHeader);

      UserInfo userInfo = null;
      if (requestHeader != null) {
        String authToken = requestHeader.trim();

        userInfo = tokenManager.getUserInfoByToken(authToken);

      } else {
        log.info("couldn't find bearer string, will ignore the header");
      }

      log.info("checking authentication for user '{}'", userInfo.getUsername());
      if (Objects.nonNull(userInfo)) {
        log.info("security context was null, so authorizating user");

        // It is not compelling necessary to load the use details from the database. You could also store the information
        // in the token and read it from it. It's up to you ;)
        UserDetails userDetails = this.userDetailsService.toSecuityUser(userInfo);

        // For simple validation it is completely sufficient to just check the token integrity. You don't have to call
        // the database compellingly. Again it's up to you ;)
        if (Objects.nonNull(userDetails)) {
          UsernamePasswordAuthenticationToken authentication =
              new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          log.info("authorizated user '{}', setting security context", userInfo.getUsername());
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    }
    chain.doFilter(request, response);
  }

  /**
   * 判断是否是白名单.
   *
   * @author zeal
   * @date 2020/1/12 22:26
   */
  private boolean passable(HttpServletRequest request) {
    AntPathMatcher pathMatcher = new AntPathMatcher();
    for (String url : filterIgnorePropertiesConfig.getUrls()) {
      if (pathMatcher.match(url, request.getRequestURI())) {
        log.info("this path authentication unnecessary!");
        return true;
      }
    }
    return false;
  }
}
