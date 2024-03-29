package com.zeal.zealsay.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.annotation.DuplicateSubmit;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.dto.request.UserAddRequest;
import com.zeal.zealsay.dto.request.UserPageRequest;
import com.zeal.zealsay.dto.request.UserRegisterRequest;
import com.zeal.zealsay.dto.request.UserUpdateRequest;
import com.zeal.zealsay.dto.response.ArticlePageResponse;
import com.zeal.zealsay.dto.response.ArticleResponse;
import com.zeal.zealsay.dto.response.UserResponse;
import com.zeal.zealsay.entity.Article;
import com.zeal.zealsay.entity.ArticleLike;
import com.zeal.zealsay.entity.User;
import com.zeal.zealsay.helper.ArticleHelper;
import com.zeal.zealsay.helper.ArticleLikeHelper;
import com.zeal.zealsay.helper.UserHelper;
import com.zeal.zealsay.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Objects;

/**
 * 用户服务入口.
 *
 * @author zhanglei
 * @date 2018/11/15  6:56 PM
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

  @Autowired
  UserService userService;
  @Autowired
  EmailService emailService;
  @Autowired
  ArticleService articleService;
  @Autowired
  BlockLogService blockLogService;
  @Autowired
  ArticleHelper articleHelper;
  @Autowired
  ArticleLikeService articleLikeService;
  @Autowired
  ArticleLikeHelper articleLikeHelper;
  @Autowired
  UserHelper userHelper;

  /**
   * 根据id来查询.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @GetMapping("/{id}")
  public Result<UserResponse> getById(@PathVariable String id) {
    log.info("开始查询用户id为 '{}' 的用户信息", id);
    return Result
        .of(userHelper.toUserResponse(userService.getById(id)));
  }

  /**
   * 查询手机号是否已被使用.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @GetMapping("/use/phone/{phone}")
  public Result<Boolean> getIsInUseByPhone(@PathVariable String phone, @RequestParam(required = false) Long userId) {
    log.info("开始查询手机号码phone为 '{}' 是否被使用", phone);
    return Result.of(userService.getIsInUseByPhone(phone, userId));
  }

  /**
   * 查询用户名是否已被使用.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @GetMapping("/use/username/{username}")
  public Result<Boolean> getIsInUseByUsername(@PathVariable String username, @RequestParam(required = false) Long userId) {
    log.info("开始查询用户名username为 '{}' 是否被使用", username);
    return Result.of(userService.getIsInUseByUsername(username, userId));
  }

  /**
   * 查询邮箱是否已被使用.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @GetMapping("/use/email/{email}")
  public Result<Boolean> getIsInUseByEmail(@PathVariable String email, @RequestParam(required = false) Long userId) {
    log.info("开始查询邮箱email为 '{}' 是否被使用", email);
    return Result.of(userService.getIsInUseByEmail(email, userId));
  }


  /**
   * 分页查询.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @GetMapping("/page")
  public Result<PageInfo<UserResponse>> getByPaginate(@RequestParam(defaultValue = "1") Long pageNumber,
                                                      @RequestParam(defaultValue = "10") Long pageSize,
                                                      UserPageRequest userPageRequest) {
    log.info("开始进行分页查询用户列表，查询参数为 '{}' ", userPageRequest);
    User user = new User();
    if (Objects.nonNull(userPageRequest)) {
      BeanUtils.copyProperties(userPageRequest,user);
    }
    Page<User> userPage = (Page<User>) userService
        .page(new Page<>(pageNumber, pageSize), userHelper
            .buildVagueQuery(user));
    return Result
        .of(userHelper.toPageInfo(userPage));
  }

  /**
   * 批量禁用用户.
   *
   * @author zhanglei
   * @date 2018/11/15  8:24 PM
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @PutMapping("disable/batch")
  public Result<Boolean> markUserDisabledBatch(@RequestBody Collection<Long> ids) {
    log.info("开始执行对用户 id 在 '{}' 内的用户执行批量封禁操作", ids.toString());
    return Result.of(userService.markUserDisabledBatch(ids));
  }

  /**
   * 批量解封用户.
   *
   * @author zhanglei
   * @date 2018/11/15  8:24 PM
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @PutMapping("unsealing/batch")
  public Result<Boolean> markUserUnsealingBatch(@RequestBody Collection<Long> ids) {
    log.info("开始执行对用户 id 在 '{}' 内的用户执行批量解封操作", ids.toString());
    return Result.of(userService.markUnsealingBatch(ids));
  }

  /**
   * 禁用单条用户.
   *
   * @author zhanglei
   * @date 2018/11/15  8:24 PM
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @PutMapping("disable/{id}")
  public Result<Boolean> markUserDisabled(@PathVariable Long id) {
    log.info("开始执行对用户 id 为 '{}' 的用户执行封禁操作", id);
    return Result.of(userService.markUserDisabled(id));
  }

  /**
   * 解封单条用户.
   *
   * @author zhanglei
   * @date 2018/11/15  8:24 PM
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @PutMapping("unsealing/{id}")
  public Result<Boolean> markUnsealing(@PathVariable Long id) {
    log.info("开始执行对用户 id 为 '{}' 的用户执行解封操作", id);
    return Result.of(userService.markUnsealing(id));
  }

  /**
   * 添加用户.
   *
   * @author zeal
   * @date 2018/11/24 13:50
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @PostMapping("")
  public Result<Boolean> addUser(@RequestBody @Validated UserAddRequest userAddRequest) {
    log.info("开始执行添加用户逻辑,新增加用户的参数为：‘{}’", userAddRequest);
    return Result.of(userService.addUser(userAddRequest));
  }

  /**
   * 修改用户.
   *
   * @author zeal
   * @date 2018/11/24 13:50
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EDITOR','ROLE_USER')")
  @PutMapping("")
  public Result<Boolean> updateUser(@RequestBody @Validated UserUpdateRequest userUpdateRequest) {
    log.info("开始执行修改用户信息逻辑,需要修改的用户的参数为：‘{}’", userUpdateRequest);
    return Result.of(userService.updateUser(userUpdateRequest));
  }

  /**
   * 用户自主注册.
   *
   * @author zhanglei
   * @date 2019-10-08  17:26
   */
  @PostMapping("register")
  public Result register(@RequestBody @Validated UserRegisterRequest userRegisterRequest) {
    log.info("开始执行用户注册服务");
    return Result.of(userService.userRegister(userRegisterRequest));
  }

  /**
   * 分页查询.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @GetMapping("/blog")
  public Result<PageInfo<ArticlePageResponse>> getBlogByPaginate(@RequestParam(defaultValue = "1") Long pageNumber,
                                                                 @RequestParam(defaultValue = "500") Long pageSize) {
    log.info("开始进行分页查询当前用户博客列表 ");
    Page<Article> articlePage = articleService
            .page(new Page<>(pageNumber, pageSize), articleHelper
                    .toCurrentUserBlog());
    return Result
            .of(articleHelper.toPageInfo(articlePage));
  }

  /**
   * 分页查询.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @GetMapping("/like")
  public Result<PageInfo<ArticleResponse>> getLikeBlogByPaginate(@RequestParam(defaultValue = "1") Long pageNumber,
                                                             @RequestParam(defaultValue = "500") Long pageSize) {
    log.info("开始进行分页查询当前用户喜欢的博客列表 ");
    Page<ArticleLike> articlePage = (Page<ArticleLike>) articleLikeService
            .page(new Page<>(pageNumber, pageSize), articleLikeHelper
                    .toCurrentUserLikeBlog());
    return Result
            .of(articleLikeHelper.toPageInfo(articlePage));
  }

  /**
   * 分页查询.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @GetMapping("/action/timelines")
  public Result<PageInfo<ArticleResponse>> getCurrentUserActions() {
    log.info("获取当前用户动态列表");
    return Result
        .of(blockLogService.getCurrentUserActions());
  }

  @DuplicateSubmit(time = 60)
  @GetMapping("/test")
  public void test() {
    try {
      Thread.sleep(5000);
      System.out.printf("hahaha");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

