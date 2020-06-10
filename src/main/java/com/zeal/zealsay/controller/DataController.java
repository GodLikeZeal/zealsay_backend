package com.zeal.zealsay.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.ImmutableMap;
import com.zeal.zealsay.common.annotation.DuplicateSubmit;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.converter.ArticleLabelConvertMapper;
import com.zeal.zealsay.converter.UserConvertMapper;
import com.zeal.zealsay.dto.request.UserAddRequest;
import com.zeal.zealsay.dto.request.UserPageRequest;
import com.zeal.zealsay.dto.request.UserRegisterRequest;
import com.zeal.zealsay.dto.request.UserUpdateRequest;
import com.zeal.zealsay.dto.response.ArticleLabelResponse;
import com.zeal.zealsay.dto.response.ArticlePageResponse;
import com.zeal.zealsay.dto.response.ArticleResponse;
import com.zeal.zealsay.dto.response.UserResponse;
import com.zeal.zealsay.entity.Article;
import com.zeal.zealsay.entity.ArticleLike;
import com.zeal.zealsay.entity.User;
import com.zeal.zealsay.feign.HitokotoClient;
import com.zeal.zealsay.feign.response.HitokotoResponse;
import com.zeal.zealsay.helper.ArticleHelper;
import com.zeal.zealsay.helper.ArticleLikeHelper;
import com.zeal.zealsay.helper.UserHelper;
import com.zeal.zealsay.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 页面数据服务入口.
 *
 * @author zhanglei
 * @date 2018/11/15  6:56 PM
 */
@Api(tags = "数据模块")
@Slf4j
@RestController
@RequestMapping("/api/v1/data")
public class DataController {

  @Autowired
  UserService userService;
  @Autowired
  EmailService emailService;
  @Autowired
  ArticleService articleService;
  @Autowired
  BlockLogService blockLogService;
  @Autowired
  PhraseService phraseService;
  @Autowired
  ArticleLabelService articleLabelService;
  @Autowired
  ArticleHelper articleHelper;
  @Autowired
  ArticleLikeService articleLikeService;
  @Autowired
  ArticleLikeHelper articleLikeHelper;
  @Autowired
  UserHelper userHelper;
  @Autowired
  UserConvertMapper userConvertMapper;
  @Autowired
  ArticleLabelConvertMapper articleLabelConvertMapper;

  /**
   * 根据id来查询.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @GetMapping("/index")
  @ApiOperation(value = "首页数据获取", notes = "首页数据获取")
  public Result<Map<String,Object>> getIndexData() throws ExecutionException, InterruptedException {
    log.info("首页数据获取中...");
    //获取五篇火热文章
    List<ArticleResponse> hotArticles = articleService.getHotArticleList().get();
    //获取一言
    HitokotoResponse hitokoto = phraseService.get().get();
    //获取标签
    List<ArticleLabelResponse> labels = articleLabelConvertMapper
            .toArticleLabelResponseList(articleLabelService.list());
    //获取文章列表
    Page<Article> articlePage = (Page<Article>) articleService
            .page(new Page<>(0, 10), articleHelper
                    .toArticlePageRequestWrapperForC());
    PageInfo<ArticlePageResponse> pageInfo = articleHelper.toPageInfo(articlePage);
    log.info("首页数据获取完毕");
    return Result
        .of(ImmutableMap.builder()
                .put("pageInfo",pageInfo)
                .put("hotArticles",hotArticles)
                .put("hitokoto",hitokoto)
                .put("labels",labels)
                .build());
  }

}

