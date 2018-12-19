package com.zeal.zealsay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeal.zealsay.entity.Article;
import com.zeal.zealsay.mapper.ArticleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author zhanglei
 * @since 2018-11-28
 */
@Service
public class ArticleService extends ServiceImpl<ArticleMapper, Article> implements IService<Article> {

}
