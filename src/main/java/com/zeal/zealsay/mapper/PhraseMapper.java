package com.zeal.zealsay.mapper;

import com.zeal.zealsay.entity.Phrase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zeal.zealsay.service.cache.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhanglei
 * @since 2019-06-30
 */
//@CacheNamespace(implementation= MybatisRedisCache.class,eviction= MybatisRedisCache.class)
public interface PhraseMapper extends BaseMapper<Phrase> {

  /**
   * 随机获取一条.
   *
   * @author  zhanglei
   * @date 2019-10-25  18:18
   */
  Phrase randomPhrase();
}
