package com.zeal.zealsay.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zeal.zealsay.common.constant.enums.BlockAction;
import com.zeal.zealsay.common.constant.enums.BlockType;
import com.zeal.zealsay.common.entity.SecuityUser;
import com.zeal.zealsay.entity.Article;
import com.zeal.zealsay.entity.ArticleLike;
import com.zeal.zealsay.entity.BlockLog;
import com.zeal.zealsay.mapper.ArticleLikeMapper;
import com.zeal.zealsay.service.auth.UserDetailServiceImpl;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


/**
 * <p>
 * 文章喜欢表 服务实现类
 * </p>
 *
 * @author zhanglei
 * @since 2019-05-16
 */
@Transactional
@Service
public class ArticleLikeService extends AbstractService<ArticleLikeMapper, ArticleLike> {

  @Autowired
  UserService userService;
  @Autowired
  ArticleService articleService;
  @Autowired
  BlockLogService blockLogService;
  @Autowired
  UserDetailServiceImpl userDetailService;

  /**
   * 判断当前用户是否喜欢过该文章.
   *
   * @author  zhanglei
   * @date 2019-11-15  17:12
   */
  public Boolean islike(@NonNull Long articleId) {
    return count(new QueryWrapper<ArticleLike>().lambda()
        .eq(ArticleLike::getArticleId, articleId)
        .eq(ArticleLike::getUserId, userDetailService.getCurrentUser().getUserId())) > 0;
  }

  /**
   * 用户喜欢操作.
   *
   * @author zhanglei
   * @date 2019-11-15  16:52
   */
  public Boolean like(@NonNull Long articleId) {
    SecuityUser user = userDetailService.getCurrentUser();
    if (Objects.isNull(user)) {
      //未登录
      return false;
    }
    //首先判断是否已经喜欢了该文章
    int count = count(new QueryWrapper<ArticleLike>().lambda()
        .eq(ArticleLike::getArticleId, articleId)
        .eq(ArticleLike::getUserId, user.getUserId()));
    if (count > 0) {
      return false;
    }
    //查询文章名称
    Article article = articleService.getById(articleId);
    if (Objects.isNull(article)) {
      //找不到该文章
      return false;
    }
    String articleName = article.getTitle();
    int likeNum = article.getLikeNum() + 1;
    //喜欢加1
    articleService.updateById(Article.builder().likeNum(likeNum).id(articleId).build());
    //保存喜欢记录
    save(ArticleLike.builder()
        .articleId(articleId)
        .articleName(articleName)
        .userId(user.getUserId())
        .userName(user.getUsername())
        .createDate(LocalDateTime.now())
        .build());
    //保存日志记录
    blockLogService.save(BlockLog.builder()
        .operatorId(user.getUserId())
        .operatorName(user.getUsername())
        .operatorDate(LocalDateTime.now())
        .action(BlockAction.LIKE_BLOG)
        .targetId(articleId)
        .targetName(articleName)
        .type(BlockType.ARTICLE)
        .build());
    return true;
  }

  /**
   * 用户不喜欢操作.
   *
   * @author zhanglei
   * @date 2019-11-15  16:52
   */
  public Boolean dislike(@NonNull Long articleId) {
    SecuityUser user = userDetailService.getCurrentUser();
    if (Objects.isNull(user)) {
      //未登录
      return false;
    }
    //首先判断是否已经喜欢了该文章
    List<ArticleLike> articleLikes = list(new QueryWrapper<ArticleLike>()
        .eq("article_id", articleId)
        .eq("user_id", user.getUserId()));

    if (CollectionUtils.isEmpty(articleLikes)) {
      return false;
    }

    ArticleLike articleLike = articleLikes.get(0);
    if (Objects.isNull(articleLike)) {
      //没喜欢，不处理
      return false;
    }
    //查询文章名称
    Article article = articleService.getById(articleId);
    if (Objects.isNull(article)) {
      //找不到该文章
      return false;
    }
    int likeNum = article.getLikeNum() - 1;
    //喜欢加1
    articleService.updateById(Article.builder().likeNum(likeNum).id(articleId).build());
    //删除记录
    removeById(articleLike.getId());
    //保存日志记录
    blockLogService.save(BlockLog.builder()
        .operatorId(user.getUserId())
        .operatorName(user.getUsername())
        .operatorDate(LocalDateTime.now())
        .action(BlockAction.DISLIKE_BLOG)
        .targetId(articleId)
        .targetName(articleLike.getArticleName())
        .type(BlockType.ARTICLE)
        .build());
    return true;
  }
}
