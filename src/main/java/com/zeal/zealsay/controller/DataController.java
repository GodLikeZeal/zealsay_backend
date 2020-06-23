package com.zeal.zealsay.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.ImmutableMap;
import com.zeal.zealsay.common.constant.enums.ResultCode;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.common.entity.SecuityUser;
import com.zeal.zealsay.converter.ArticleLabelConvertMapper;
import com.zeal.zealsay.converter.RoleConvertMapper;
import com.zeal.zealsay.converter.UserConvertMapper;
import com.zeal.zealsay.dto.request.*;
import com.zeal.zealsay.dto.response.*;
import com.zeal.zealsay.entity.*;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.feign.response.HitokotoResponse;
import com.zeal.zealsay.helper.ArticleHelper;
import com.zeal.zealsay.helper.ArticleLikeHelper;
import com.zeal.zealsay.helper.UserHelper;
import com.zeal.zealsay.service.*;
import com.zeal.zealsay.service.auth.UserDetailServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
  RoleService roleService;
  @Autowired
  EmailService emailService;
  @Autowired
  ArticleService articleService;
  @Autowired
  BlockLogService blockLogService;
  @Autowired
  DictService dictService;
  @Autowired
  PhraseService phraseService;
  @Autowired
  ArticleLabelService articleLabelService;
  @Autowired
  ArticleLikeService articleLikeService;
  @Autowired
  ArticleCategoryService articleCategoryService;
  @Autowired
  ArticleHelper articleHelper;
  @Autowired
  UserDetailServiceImpl userDetailService;
  @Autowired
  ArticleLikeHelper articleLikeHelper;
  @Autowired
  UserHelper userHelper;
  @Autowired
  UserConvertMapper userConvertMapper;
  @Autowired
  ArticleLabelConvertMapper articleLabelConvertMapper;
  @Autowired
  RoleConvertMapper roleConvertMapper;

  /**
   * 用户中心信息获取.
   *
   * @author zhanglei
   * @date 2020/6/11  4:34 下午
   */
  @GetMapping("/user/{id}")
  @ApiOperation(value = "用户中心信息获取", notes = "用户中心信息获取")
  public Result<Map<String, Object>> getUserData(@PathVariable Long id) throws ExecutionException, InterruptedException {
    log.info("👕用户中心信息获取中...");
    //获取当前用户信息
    UserResponse user = userConvertMapper.toUserResponse(userService.getById(id));
    if (Objects.isNull(user)) {
      throw new ServiceException(ResultCode.NOT_FOUND.getCode(), "用户不存在");
    }
    Page<Article> articlePage = articleService
        .page(new Page<>(1, 500), new QueryWrapper<Article>().eq("author_id",id));
    PageInfo<ArticlePageResponse> userPage = articleHelper.toPageInfo(articlePage);

    //获取收藏列表
    Page<ArticleLike> likePages = articleLikeService
        .page(new Page<>(1, 500), new QueryWrapper<ArticleLike>().eq("user_id",id));
    PageInfo<ArticleResponse> likePage = articleLikeHelper.toPageInfo(likePages);

    //获取动态
    List<BlockLog> actions = blockLogService.getUserActions(id);

    //获取省份
    List<Dict> provinces = dictService.getProvinceList().get();

    //获取分类
    List<ArticleCategoryResponse> categorys = articleCategoryService.getCategoryList().get();

    //角色信息
    List<RoleResponse> roles = roleConvertMapper.toRoleResponseList(roleService.list());

    log.info("👕用户中心信息获取完毕");
    return Result
        .of(ImmutableMap.builder()
            .put("user", user)
            .put("userPage", userPage)
            .put("likePage", likePage)
            .put("actions", actions)
            .put("categorys", categorys)
            .put("roles", roles)
            .put("province", provinces)
            .build());
  }

}

