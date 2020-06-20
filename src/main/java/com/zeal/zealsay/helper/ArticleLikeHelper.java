package com.zeal.zealsay.helper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.SecuityUser;
import com.zeal.zealsay.converter.ArticleConvertMapper;
import com.zeal.zealsay.dto.response.ArticleResponse;
import com.zeal.zealsay.entity.Article;
import com.zeal.zealsay.entity.ArticleCategory;
import com.zeal.zealsay.entity.ArticleLike;
import com.zeal.zealsay.entity.User;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.service.ArticleCategoryService;
import com.zeal.zealsay.service.ArticleService;
import com.zeal.zealsay.service.UserService;
import com.zeal.zealsay.service.auth.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 文章帮助类.
 *
 * @author zhanglei
 * @date 2018/11/15  6:53 PM
 */
@Component
public class ArticleLikeHelper {

  @Autowired
  ArticleConvertMapper articleConvertMapper;
  @Autowired
  UserDetailServiceImpl userDetailService;
  @Autowired
  UserService userService;
  @Autowired
  ArticleCategoryService articleCategoryService;
  @Autowired
  ArticleService articleService;

  /**
   * 转换成返回列表.
   *
   * @param articlePage
   * @author zhanglei
   * @date 2018/11/15  9:25 PM
   */
  public PageInfo<ArticleResponse> toPageInfo(Page<ArticleLike> articlePage) {
    PageInfo<Article> articlePageInfo = new PageInfo(articlePage);
    List<ArticleResponse> articleResponses = articlePage.getRecords()
        .stream()
        .map(this::apply)
        .collect(Collectors.toList());
    return PageInfo.<ArticleResponse>builder()
        .records(articleResponses)
        .currentPage(articlePageInfo.getCurrentPage())
        .pageSize(articlePageInfo.getPageSize())
        .total(articlePageInfo.getTotal())
        .build();
  }

  /**
   * 解析分类目录和作者信息.
   *
   * @author  zhanglei
   * @date 2019-07-29  18:22
   */
  private ArticleResponse apply(Article s) {
    ArticleResponse articleResponse = articleConvertMapper.toArticleResponse(s);
    //解析分类目录
    ArticleCategory articleCategory = Optional
        .ofNullable(articleCategoryService.getById(s.getCategoryId()))
        .orElse(ArticleCategory.builder()
            .name("无")
            .build());
    articleResponse.setCategoryName(articleCategory.getName());
    //解析作者
    User user = Optional.ofNullable(userService.getById(s.getAuthorId()))
        .orElse(User.builder()
            .username("佚名")
            .build());
    articleResponse.setAuthorName(user.getUsername());
    articleResponse.setAuthorAvatar(user.getAvatar());
    return articleResponse;
  }

  /**
  * 构造返回对象.
  *
  * @author  zeal
  * @date 2019/11/15 22:13
  */
  private ArticleResponse apply(ArticleLike s) {
    Article article = articleService.getById(s.getArticleId());
    return apply(article);
  }

  /**
   * 查当前用户喜欢的blog.
   *
   * @author zhanglei
   * @date 2019-07-05  16:17
   */
  public QueryWrapper<ArticleLike> toCurrentUserLikeBlog() {
    QueryWrapper<ArticleLike> wrapper = new QueryWrapper<>();
    //过滤掉下架的和草稿
    SecuityUser user = userDetailService.getCurrentUser();
    if (Objects.nonNull(user)) {
      wrapper.eq("user_id", user.getUserId());
    } else {
      throw new ServiceException("请重新登录后再试!");
    }
    return wrapper;
  }
}
