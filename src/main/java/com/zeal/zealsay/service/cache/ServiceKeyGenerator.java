package com.zeal.zealsay.service.cache;



import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.lang.reflect.Method;

/**
 * 自定义cache key生成器.
 *
 * @author  zhanglei
 * @date 2019-11-05  17:20
 */
@Slf4j
@Component
public class ServiceKeyGenerator implements KeyGenerator {
  @Override
  public Object generate(Object o, Method method, Object... objects) {
    StringBuilder sb = new StringBuilder();
    sb.append(o.getClass());
    sb.append(method.getName());
    for (Object obj : objects) {
      if (obj != null) {
        sb.append("&");
        sb.append(obj.getClass().getName());
        sb.append("&");
        sb.append(JSONObject.toJSON(obj).toString());
      }
    }
    log.info("redis cache key str:{} " , sb.toString());
    log.info("redis cache key sha256Hex:{} " , DigestUtils.md5DigestAsHex(sb.toString().getBytes()));
    return DigestUtils.md5DigestAsHex(sb.toString().getBytes());
  }
}
