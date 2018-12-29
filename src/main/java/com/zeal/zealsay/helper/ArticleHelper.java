package com.zeal.zealsay.helper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.converter.ArticleConvertMapper;
import com.zeal.zealsay.converter.RoleConvertMapper;
import com.zeal.zealsay.dto.request.ArticleAddRequest;
import com.zeal.zealsay.dto.request.ArticleUpdateRequest;
import com.zeal.zealsay.dto.request.RoleAddResquest;
import com.zeal.zealsay.dto.request.RoleUpdateRequest;
import com.zeal.zealsay.dto.response.ArticleResponse;
import com.zeal.zealsay.dto.response.RoleResponse;
import com.zeal.zealsay.entity.Article;
import com.zeal.zealsay.entity.Role;
import com.zeal.zealsay.service.auth.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色帮助类.
 *
 * @author  zhanglei
 * @date 2018/11/15  6:53 PM
 */
@Component
public class ArticleHelper {

  @Autowired
  ArticleConvertMapper articleConvertMapper;
  @Autowired
  UserDetailServiceImpl userDetailService;

  /**
   * 更新之前通过请求参数转换成Article.
   *
   * @author  zhanglei
   * @date 2018/11/15  7:46 PM
   */
  public Article initBeforeUpdate(ArticleUpdateRequest articleUpdateRequest){
    Article article = articleConvertMapper.toArticle(articleUpdateRequest);
    article.setUpdateAt(LocalDateTime.now());
    return article;
  }

  /**
   * 添加之前通过请求参数转换成Article.
   *
   * @author  zhanglei
   * @date 2018/11/15  7:46 PM
   */
  public Article initBeforeAdd(ArticleAddRequest articleAddRequest){
    Article article = articleConvertMapper.toArticle(articleAddRequest);
    article.setCreateAt(LocalDateTime.now());
    article.setAuthorId(userDetailService.getCurrentUser().getUserId());
    return article;
  }

  /**
   * 转换成返回列表.
   *
   * @author  zhanglei
   * @date 2018/11/15  9:25 PM
   * @param articlePage
   */
  public PageInfo<ArticleResponse> toPageInfo(Page<Article> articlePage){
    PageInfo<Article> articlePageInfo = new PageInfo(articlePage);
    List<ArticleResponse> articleResponses = articlePage.getRecords()
        .stream()
        .map(s -> articleConvertMapper.toArticleResponse(s))
        .collect(Collectors.toList());
    return PageInfo.<ArticleResponse>builder()
        .records(articleResponses)
        .currentPage(articlePageInfo.getCurrentPage())
        .pageSize(articlePageInfo.getPageSize())
        .total(articlePageInfo.getTotal())
        .build();
  }
}
