package com.zeal.zealsay.mapper;

import com.zeal.zealsay.entity.ArticleCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zeal.zealsay.service.cache.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * 分类目录表 Mapper 接口
 * </p>
 *
 * @author zhanglei
 * @since 2018-12-29
 */
//@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface ArticleCategoryMapper extends BaseMapper<ArticleCategory> {

}
