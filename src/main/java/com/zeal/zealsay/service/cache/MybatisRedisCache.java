package com.zeal.zealsay.service.cache;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.zeal.zealsay.common.biz.SpringBeanTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用redis来进行二级缓存.
 *
 * @author  zhanglei
 * @date 2020/1/17  12:29 下午
 */
@Slf4j
public class MybatisRedisCache implements Cache {
  // 读写锁
  private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

  //这里使用了redis缓存，使用springboot自动注入
  private RedisTemplate<String, Object> redisTemplate;


  private String id;

  public MybatisRedisCache(final String id) {
    if (id == null) {
      throw new IllegalArgumentException("Cache instances require an ID");
    }
    this.id = id;
  }

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public void putObject(Object key, Object value) {
    log.info("存入缓存,key: {}",key);
    if (redisTemplate == null) {
      //由于启动期间注入失败，只能运行期间注入，这段代码可以删除
      redisTemplate = (RedisTemplate<String, Object>) SpringBeanTool.getBean("redisTemplate");
    }
    if (value != null) {
      redisTemplate.opsForValue().set(key.toString(), value);
    }
  }

  @Override
  public Object getObject(Object key) {
    log.info("获取缓存,key: {}",key);
    if (redisTemplate == null) {
      //由于启动期间注入失败，只能运行期间注入，这段代码可以删除
      redisTemplate = (RedisTemplate<String, Object>) SpringBeanTool.getBean("redisTemplate");
    }
    try {
      if (key != null) {
        return redisTemplate.opsForValue().get(key.toString());
      }
    } catch (Exception e) {
      e.printStackTrace();
      log.error("缓存出错 ");
    }
    return null;
  }

  @Override
  public Object removeObject(Object key) {
    log.info("移除缓存,key: {}",key);
    if (redisTemplate == null) {
      //由于启动期间注入失败，只能运行期间注入，这段代码可以删除
      redisTemplate = (RedisTemplate<String, Object>) SpringBeanTool.getBean("redisTemplate");
    }
    if (key != null) {
      redisTemplate.delete(key.toString());
    }
    return null;
  }

  @Override
  public void clear() {
    log.info("清空缓存");
    if (redisTemplate == null) {
      //由于启动期间注入失败，只能运行期间注入，这段代码可以删除
      redisTemplate = (RedisTemplate<String, Object>) SpringBeanTool.getBean("redisTemplate");
    }
    Set<String> keys = redisTemplate.keys("*:" + this.id + "*");
    if (!CollectionUtils.isEmpty(keys)) {
      redisTemplate.delete(keys);
    }
  }

  @Override
  public int getSize() {
    Long size = redisTemplate.execute((RedisCallback<Long>) RedisServerCommands::dbSize);
    return size.intValue();
  }

  @Override
  public ReadWriteLock getReadWriteLock() {
    return this.readWriteLock;
  }
}
