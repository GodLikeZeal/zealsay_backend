package com.zeal.zealsay.common.aop;

import com.zeal.zealsay.common.annotation.DuplicateSubmit;
import com.zeal.zealsay.common.constant.enums.ResultCode;
import com.zeal.zealsay.common.entity.SecuityUser;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.service.auth.UserDetailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
public class DuplicateSubmitAspect {

  public static final String DUPLICATE_TOKEN_KEY = "duplicate_token_key";

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;
  @Autowired
  private UserDetailServiceImpl userDetailService;

  @Pointcut("@annotation(duplicateSubmit)")
  public void pointCut(DuplicateSubmit duplicateSubmit) {

  }

  /**
   * 防止重复提交.
   *
   * @param pjp             a
   * @param duplicateSubmit 注解
   * @return
   */
  @Around("pointCut(duplicateSubmit)")
  public Object arround(ProceedingJoinPoint pjp, DuplicateSubmit duplicateSubmit) {
    ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
    try {
      String key = getDuplicateTokenKey(pjp, duplicateSubmit);
      if (opsForValue.get(key) == null) {
        // 如果缓存中有这个url视为重复提交
        Object o = pjp.proceed();
        opsForValue.set(key, 0, duplicateSubmit.time(), TimeUnit.SECONDS);
        return o;
      } else {
        log.error("重复提交");
        throw new ServiceException(ResultCode.REQUEST_FREQUENTLY.getCode(),
            ResultCode.REQUEST_FREQUENTLY.getMessage());
      }
    } catch (Throwable e) {
      e.printStackTrace();
      log.error("验证重复提交时出现未知异常!");
      return null;
    }
  }

  /**
   * 获取重复提交key.
   *
   * @param joinPoint j
   * @return
   */
  public String getDuplicateTokenKey(ProceedingJoinPoint joinPoint, DuplicateSubmit duplicateSubmit) {
    String methodName = joinPoint.getSignature().getName();
    StringBuilder key = new StringBuilder(DUPLICATE_TOKEN_KEY);
    if (duplicateSubmit.type() == DuplicateSubmit.SESSION) {
      SecuityUser secuityUser = userDetailService.getCurrentUser();
      key.append(",").append(secuityUser.getUserId());
    }
    key.append(",").append(methodName);
    return key.toString();
  }
}
