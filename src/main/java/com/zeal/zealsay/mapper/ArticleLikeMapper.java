package com.zeal.zealsay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zeal.zealsay.entity.ArticleLike;
import com.zeal.zealsay.service.cache.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * 文章喜欢表 Mapper 接口
 * </p>
 *
 * @author zhanglei
 * @since 2019-05-16
 */
@CacheNamespace(implementation= MybatisRedisCache.class,eviction= MybatisRedisCache.class)
public interface ArticleLikeMapper extends BaseMapper<ArticleLike> {

}
