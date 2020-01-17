package com.zeal.zealsay.mapper;

import com.zeal.zealsay.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zeal.zealsay.service.cache.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhanglei
 * @since 2018-09-14
 */
@CacheNamespace(implementation= MybatisRedisCache.class,eviction= MybatisRedisCache.class)
public interface RoleMapper extends BaseMapper<Role> {

}
