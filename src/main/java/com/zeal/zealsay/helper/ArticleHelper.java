package com.zeal.zealsay.helper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.constant.enums.ArticleStatus;
import com.zeal.zealsay.common.constant.enums.Openness;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.SecuityUser;
import com.zeal.zealsay.converter.ArticleConvertMapper;
import com.zeal.zealsay.dto.request.ArticleAddRequest;
import com.zeal.zealsay.dto.request.ArticlePageRequest;
import com.zeal.zealsay.dto.request.ArticleUpdateRequest;
import com.zeal.zealsay.dto.response.ArticlePageResponse;
import com.zeal.zealsay.dto.response.ArticleResponse;
import com.zeal.zealsay.entity.Article;
import com.zeal.zealsay.entity.ArticleCategory;
import com.zeal.zealsay.entity.User;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.mapper.ArticleMapper;
import com.zeal.zealsay.service.ArticleCategoryService;
import com.zeal.zealsay.service.UserService;
import com.zeal.zealsay.service.auth.UserDetailServiceImpl;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
public class ArticleHelper {

  @Autowired
  ArticleConvertMapper articleConvertMapper;
  @Autowired
  UserDetailServiceImpl userDetailService;
  @Autowired
  UserService userService;
  @Autowired
  ArticleCategoryService articleCategoryService;
  @Autowired
  ArticleMapper articleMapper;

  /**
   * 更新之前通过请求参数转换成Article.
   *
   * @author zhanglei
   * @date 2018/11/15  7:46 PM
   */
  public Article initBeforeUpdate(ArticleUpdateRequest articleUpdateRequest) {
    Article article = articleConvertMapper.toArticle(articleUpdateRequest);
    article.setUpdateDate(LocalDateTime.now());
    return article;
  }

  /**
   * 添加之前通过请求参数转换成Article.
   *
   * @author zhanglei
   * @date 2018/11/15  7:46 PM
   */
  public Article initBeforeAdd(ArticleAddRequest articleAddRequest) {
    Article article = articleConvertMapper.toArticle(articleAddRequest);
    article.setCreateDate(LocalDateTime.now());
    article.setAuthorId(userDetailService.getCurrentUser().getUserId());
    article.setReadNum(0);
    article.setLikeNum(0);
    article.setIsDel(false);
    return article;
  }

  /**
   * 转换成返回列表.
   *
   * @param articlePage
   * @author zhanglei
   * @date 2018/11/15  9:25 PM
   */
  public PageInfo<ArticlePageResponse> toPageInfo(Page<Article> articlePage) {
    PageInfo<Article> articlePageInfo = new PageInfo(articlePage);
    List<ArticlePageResponse> articleResponses = articlePage.getRecords()
        .stream()
        .map(this::applyPage)
        .collect(Collectors.toList());
    return PageInfo.<ArticlePageResponse>builder()
        .records(articleResponses)
        .currentPage(articlePageInfo.getCurrentPage())
        .pageSize(articlePageInfo.getPageSize())
        .total(articlePageInfo.getTotal())
        .build();
  }

  /**
   * c端查询.
   * @param pageNumber 当前页数
   * @param pageSize 页面大小
   * @return
   */
  public PageInfo<ArticlePageResponse> toPageInfo(Long pageNumber,Long pageSize) {
    //如果是openess为自己的，需要增加筛选条件
    Long userId = null;
    SecuityUser user = userDetailService.getCurrentUser();
    if (Objects.nonNull(user)) {
      userId = user.getUserId();
    }

    List<ArticlePageResponse> articleResponses = articleMapper.getPage(userId,pageSize*(pageNumber -1),pageSize);
    Long total = articleMapper.getPageCount(userId,pageSize*(pageNumber -1),pageSize);
    return PageInfo.<ArticlePageResponse>builder()
        .records(articleResponses)
        .currentPage(pageNumber)
        .pageSize(pageSize)
        .total(total)
        .build();
  }

  /**
   * 构造文章列表分页查询条件.
   *
   * @author zhanglei
   * @date 2019-05-10  11:25
   */
  public QueryWrapper<Article> toAeticlePageRequestWrapper(@NonNull ArticlePageRequest pageRequest) {
    //构造分页查询条件
    QueryWrapper<Article> wrapper = new QueryWrapper<>();
    //模糊检索标题
    if (StringUtils.isNotBlank(pageRequest.getTitle())) {
      wrapper.lambda().like(Article::getTitle, pageRequest.getTitle());
    }
    //模糊检索标签
    if (StringUtils.isNotBlank(pageRequest.getLabel())) {
      wrapper.lambda().like(Article::getLabel, pageRequest.getLabel());
    }
    //检索分类目录
    if (Objects.nonNull(pageRequest.getCategoryId())) {
      wrapper.lambda().eq(Article::getCategoryId, pageRequest.getCategoryId());
    }
    //模糊检索作者名称或者手机号
    if (StringUtils.isNotBlank(pageRequest.getAuthorName()) || StringUtils.isNotBlank(pageRequest.getAuthorPhone())) {
      List<User> users = userService.list(new QueryWrapper<User>().lambda()
          .like(User::getUsername, pageRequest.getAuthorName())
          .like(User::getPhoneNumber, pageRequest.getAuthorPhone()));
      wrapper.lambda().in(Article::getAuthorId, users.stream().map(u -> u.getId()).collect(Collectors.toList()));
    }
    //检索时间段
    if (Objects.nonNull(pageRequest.getStartDate())) {
      wrapper.lambda().ge(Article::getCreateDate, pageRequest.getStartDate());
    }
    if (Objects.nonNull(pageRequest.getEndDate())) {
      wrapper.lambda().le(Article::getCreateDate, pageRequest.getEndDate());
    }
    //按照创建时间倒叙排序
    wrapper.orderByDesc("create_date");
    return wrapper;
  }

  /**
   * 博客端需要过滤掉未上架的.
   *
   * @author zhanglei
   * @date 2019-07-05  16:17
   */
  public QueryWrapper<Article> toAeticlePageRequestWrapperForC(@NonNull ArticlePageRequest pageRequest) {
    QueryWrapper<Article> wrapper = toAeticlePageRequestWrapper(pageRequest);
    //过滤掉下架的和草稿
    wrapper.lambda().eq(Article::getStatus, ArticleStatus.FORMAL);
    wrapper.lambda().eq(Article::getOpenness, Openness.ALL);
    //如果是openess为自己的，需要增加筛选条件
    SecuityUser user = userDetailService.getCurrentUser();
    if (Objects.nonNull(user)) {
      wrapper.or(articleQueryWrapper -> articleQueryWrapper
          .eq("author_id", user.getUserId())
          .eq("openness", Openness.SELFONLY));
    }
    return wrapper;
  }

  /**
   * 构建返回对象.
   *
   * @author  zhanglei
   * @date 2019-07-29  18:19
   */
  public ArticleResponse toArticleResponse(Article article){
    return this.apply(article);
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
   * 解析分类目录和作者信息.
   *
   * @author  zhanglei
   * @date 2019-07-29  18:22
   */
  private ArticlePageResponse applyPage(Article s) {
    ArticlePageResponse articleResponse = articleConvertMapper.toArticlePageResponse(s);
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
   * 查当前用户的blog.
   *
   * @author zhanglei
   * @date 2019-07-05  16:17
   */
  public QueryWrapper<Article> toCurrentUserBlog() {
    QueryWrapper<Article> wrapper = new QueryWrapper<>();
    SecuityUser user = userDetailService.getCurrentUser();
    if (Objects.nonNull(user)) {
      wrapper.eq("author_id",user.getUserId());
    } else {
      throw new ServiceException("请重新登录后再试!");
    }
    return wrapper;
  }

}
