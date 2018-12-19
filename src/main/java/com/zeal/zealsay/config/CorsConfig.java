package com.zeal.zealsay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局跨域配置.
 *
 * @author zhanglei
 * @date 2018/10/18  下午4:16
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {


  @Override
  public void addCorsMappings(CorsRegistry registry) {
    //设置允许跨域的路径
    registry.addMapping("/**")
        //设置允许跨域请求的域名
        .allowedOrigins("*")
        //是否允许证书 不再默认开启
        .allowCredentials(true)
        //设置允许的方法
        .allowedMethods("*")
        //跨域允许时间
        .maxAge(3600);
  }

  @Bean
  public CorsFilter corsFilter() {
    final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.addAllowedOrigin("*");
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.addAllowedMethod("*");
    urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
    return new CorsFilter(urlBasedCorsConfigurationSource);
  }
}
