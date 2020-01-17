package com.zeal.zealsay.mapper;

import com.zeal.zealsay.entity.LoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zeal.zealsay.service.cache.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * 登录记录表 Mapper 接口
 * </p>
 *
 * @author zhanglei
 * @since 2019-03-16
 */
@CacheNamespace(implementation= MybatisRedisCache.class,eviction= MybatisRedisCache.class)
public interface LoginLogMapper extends BaseMapper<LoginLog> {

}
