package com.zeal.zealsay.mapper;

import com.zeal.zealsay.entity.AuthUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zeal.zealsay.service.cache.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * 第三方登录用户信息 Mapper 接口
 * </p>
 *
 * @author zhanglei
 * @since 2019-09-12
 */
@CacheNamespace(implementation= MybatisRedisCache.class,eviction= MybatisRedisCache.class)
public interface AuthUserMapper extends BaseMapper<AuthUser> {

}
