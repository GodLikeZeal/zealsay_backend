package com.zeal.zealsay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zeal.zealsay.entity.Comment;
import com.zeal.zealsay.service.cache.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;


/**
 * 评论模块mapper接口.
 *
 * @author  zhanglei
 * @date 2020/6/16  4:57 下午
 */
@CacheNamespace(implementation= MybatisRedisCache.class,eviction= MybatisRedisCache.class)
public interface CommentMapper extends BaseMapper<Comment> {

}
