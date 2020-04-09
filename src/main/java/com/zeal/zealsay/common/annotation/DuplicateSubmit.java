package com.zeal.zealsay.common.annotation;

import java.lang.annotation.*;

/**
 * 防止重复提交注解.
 *
 * @author zhanglei
 * @date 2020/2/10  3:51 下午
 */
@Retention(RetentionPolicy.RUNTIME) //运行时候有效
@Target(ElementType.METHOD) //作用到方法上
@Documented
public @interface DuplicateSubmit {
  /**
   * 针对系统请求.
   */
  int REQUEST = 1;
  /**
   * 针对当前用户.
   */
  int SESSION = 2;

  /**
   * 默认时间为2秒.
   */
  long time() default 2;

  /**
   * 防止重复提交类型，默认：一次请求完成之前防止重复提交.
   */
  int type() default REQUEST;
}