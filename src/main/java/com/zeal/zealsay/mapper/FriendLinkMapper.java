package com.zeal.zealsay.mapper;

import com.zeal.zealsay.entity.FriendLink;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zeal.zealsay.service.cache.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * 友链记录表 Mapper 接口
 * </p>
 *
 * @author zhanglei
 * @since 2019-07-31
 */
//@CacheNamespace(implementation= MybatisRedisCache.class,eviction= MybatisRedisCache.class)
public interface FriendLinkMapper extends BaseMapper<FriendLink> {

}
