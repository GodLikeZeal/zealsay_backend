package com.zeal.zealsay.mapper;

import com.zeal.zealsay.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zeal.zealsay.service.cache.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * 全局字典表 Mapper 接口
 * </p>
 *
 * @author zhanglei
 * @since 2019-03-27
 */
@CacheNamespace(implementation= MybatisRedisCache.class,eviction= MybatisRedisCache.class)
public interface DictMapper extends BaseMapper<Dict> {

}
