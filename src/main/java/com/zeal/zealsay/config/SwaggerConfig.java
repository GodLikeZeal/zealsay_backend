package com.zeal.zealsay.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger rest api 配置类.
 *
 * @author zhanglei
 * @date 2018/9/26  下午8:46
 */
@Configuration
@EnableSwaggerBootstrapUI
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.zeal.zealsay"))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("zealsay rest api 统一管理系统")
        .description("接口管理系统")
        .license("license by zeal")
        .termsOfServiceUrl("http://www.zealsay.com/")
        .contact("zeal and jinjin")
        .version("1.0.0")
        .build();
  }
}
