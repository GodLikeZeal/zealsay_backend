package com.zeal.zealsay.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zeal.zealsay.common.constant.enums.*;
import com.zeal.zealsay.converter.ArticleConvertMapper;
import com.zeal.zealsay.dto.request.ArticleAddRequest;
import com.zeal.zealsay.dto.request.ArticleUpdateRequest;
import com.zeal.zealsay.entity.Article;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.helper.ArticleHelper;
import com.zeal.zealsay.mapper.ArticleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeal.zealsay.service.auth.UserDetailServiceImpl;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
  @Autowired
  BlockLogService blockLogService;

  /**
   * 添加文章.
   *
   * @author  zhanglei
   * @date 2018/12/29  5:07 PM
   */
  public Boolean addArticle(ArticleAddRequest articleAddRequest) {
    Article article = articleHelper.initBeforeAdd(articleAddRequest);
    List<Article> articles = list(new QueryWrapper<Article>().lambda()
        .eq(Article::getTitle,articleAddRequest.getTitle()));
    if (Objects.nonNull(articles) && articles.size() > 0) {
      throw new ServiceException("请勿重复添加文章");
    }
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

  /**
   * 根据id来上架文章作品.
   *
   * @author  zhanglei
   * @date 2019-05-15  11:16
   */
  public Boolean markArticleUp(Long id) {
    //记录
    Article article = getById(id);

    if (article.getStatus().equals(ArticleStatus.FORMAL)) {
      throw new ServiceException("该作品已发布，请不要重复操作上架");
    }

    blockLogService.saveBlocak(article, BlockType.ARTICLE, BlockAction.UP,"");

    return updateById(Article.builder()
        .id(id)
        .status(ArticleStatus.FORMAL)
        .build());
  }

  /**
   * 根据id来下架文章作品.
   *
   * @author  zhanglei
   * @date 2019-05-15  11:16
   */
  public Boolean markArticleDown(Long id) {

    Article article = getById(id);

    if (article.getStatus().equals(ArticleStatus.DOWN)) {
      throw new ServiceException("该作品已下架，请不要重复操作下架");
    }
    if (article.getStatus().equals(ArticleStatus.DRAFT)) {
      throw new ServiceException("该作品还未发布，无法下架");
    }

    //记录
    blockLogService.saveBlocak(article, BlockType.ARTICLE, BlockAction.DOWN,"违禁");

    return updateById(Article.builder()
        .id(id)
        .status(ArticleStatus.DOWN)
        .build());
  }

  /**
   * 批量下架文章作品.
   *
   * @author  zhanglei
   * @date 2019-05-15  11:30
   */
  public Boolean markArticleDown(@NonNull Collection<Long> ids) {

    List<Article> articles = (List<Article>) listByIds(ids);

    //记录
    blockLogService.saveBlocakArticleBatch(articles, BlockType.ARTICLE, BlockAction.BAN,"违禁");

    update(Article.builder().status(ArticleStatus.DOWN).build(), new UpdateWrapper<Article>()
        .in("id", ids));
    return true;
  }
}
