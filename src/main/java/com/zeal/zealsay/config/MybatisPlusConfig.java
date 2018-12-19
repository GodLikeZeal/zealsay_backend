package com.zeal.zealsay.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybtis-plus配置类.
 *
 * @author zhanglei
 * @date 2018/9/17  上午11:57
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.zeal.zealsay.mapper")
public class MybatisPlusConfig {

  /**
   * SQL执行效率插件.
   */
  @Bean
  @Profile({"dev", "test", "prd"})
  public PerformanceInterceptor performanceInterceptor() {
    return new PerformanceInterceptor();
  }

  /**
   * 乐观锁插件.
   */
  @Bean
  public OptimisticLockerInterceptor optimisticLockerInterceptor() {
    return new OptimisticLockerInterceptor();
  }
  /**
   * 分页插件.
   */
  @Bean
  public PaginationInterceptor paginationInterceptor() {
    return new PaginationInterceptor();
  }

  /**
   * 逻辑删除.
   *
   * @author  zhanglei
   * @date 2018/9/19  下午7:06
   */
  @Bean
  public ISqlInjector sqlInjector() {
    return new LogicSqlInjector();
  }
}
