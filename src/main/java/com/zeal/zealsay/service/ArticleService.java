package com.zeal.zealsay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeal.zealsay.converter.ArticleConvertMapper;
import com.zeal.zealsay.dto.request.ArticleAddRequest;
import com.zeal.zealsay.dto.request.ArticleUpdateRequest;
import com.zeal.zealsay.entity.Article;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.helper.ArticleHelper;
import com.zeal.zealsay.mapper.ArticleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeal.zealsay.service.auth.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author zhanglei
 * @since 2018-11-28
 */
@Transactional(rollbackFor = {ServiceException.class,RuntimeException.class,Exception.class})
@Service
public class ArticleService extends ServiceImpl<ArticleMapper, Article> implements IService<Article> {

  @Autowired
  ArticleConvertMapper articleConvertMapper;
  @Autowired
  UserDetailServiceImpl userDetailService;
  @Autowired
  ArticleHelper articleHelper;

  /**
   * 添加文章.
   *
   * @author  zhanglei
   * @date 2018/12/29  5:07 PM
   */
  public Boolean addArticle(ArticleAddRequest articleAddRequest) {
    Article article = articleHelper.initBeforeAdd(articleAddRequest);
    return save(article);
  }

  /**
   * 修改文章.
   *
   * @author  zhanglei
   * @date 2018/12/29  5:07 PM
   */
  public Boolean updateArticle(ArticleUpdateRequest articleUpdateRequest) {
    Article article = articleHelper.initBeforeUpdate(articleUpdateRequest);
    return updateById(article);
  }
}
