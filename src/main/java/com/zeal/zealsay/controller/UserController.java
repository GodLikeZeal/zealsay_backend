package com.zeal.zealsay.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.converter.UserConvertMapper;
import com.zeal.zealsay.dto.request.UserAddRequest;
import com.zeal.zealsay.dto.request.UserPageRequest;
import com.zeal.zealsay.dto.request.UserRegisterRequest;
import com.zeal.zealsay.dto.request.UserUpdateRequest;
import com.zeal.zealsay.dto.response.ArticleResponse;
import com.zeal.zealsay.dto.response.UserResponse;
import com.zeal.zealsay.entity.Article;
import com.zeal.zealsay.entity.ArticleLike;
import com.zeal.zealsay.entity.User;
import com.zeal.zealsay.helper.ArticleHelper;
import com.zeal.zealsay.helper.ArticleLikeHelper;
import com.zeal.zealsay.helper.UserHelper;
import com.zeal.zealsay.service.ArticleLikeService;
import com.zeal.zealsay.service.ArticleService;
import com.zeal.zealsay.service.EmailService;
import com.zeal.zealsay.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

/**
 * 用户服务入口.
 *
 * @author zhanglei
 * @date 2018/11/15  6:56 PM
 */
@Api(tags = "用户模块")
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
  ArticleHelper articleHelper;
  @Autowired
  ArticleLikeService articleLikeService;
  @Autowired
  ArticleLikeHelper articleLikeHelper;
  @Autowired
  UserHelper userHelper;
  @Autowired
  UserConvertMapper userConvertMapper;

  /**
   * 根据id来查询.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @GetMapping("/{id}")
  @ApiOperation(value = "根据id获取用户信息", notes = "根据id获取用户信息")
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
  @ApiOperation(value = "查询手机号是否已被使用", notes = "查询手机号是否已被使用")
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
  @ApiOperation(value = "查询用户名是否已被使用", notes = "查询用户名是否已被使用")
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
  @ApiOperation(value = "查询邮箱是否已被使用", notes = "查询邮箱是否已被使用")
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
  @ApiOperation(value = "分页获取用户信息列表", notes = "分页获取用户信息列表")
  public Result<PageInfo<UserResponse>> getByPaginate(@RequestParam(defaultValue = "1") Long pageNumber,
                                                      @RequestParam(defaultValue = "10") Long pageSize,
                                                      UserPageRequest userPageRequest) {
    log.info("开始进行分页查询用户列表，查询参数为 '{}' ", userPageRequest);
    Page<User> userPage = (Page<User>) userService
        .page(new Page<>(pageNumber, pageSize), userHelper
            .buildVagueQuery(userConvertMapper
                .toUser(userPageRequest)));
    return Result
        .of(userHelper.toPageInfo(userPage));
  }

  /**
   * 批量禁用用户.
   *
   * @author zhanglei
   * @date 2018/11/15  8:24 PM
   */
  @PutMapping("disable/batch")
  @ApiOperation(value = "根据id列表批量禁用用户", notes = "根据id列表批量禁用用户")
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
  @PutMapping("unsealing/batch")
  @ApiOperation(value = "根据id列表批量解封用户", notes = "根据id列表批量解封用户")
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
  @PutMapping("disable/{id}")
  @ApiOperation(value = "根据id禁用用户", notes = "根据id禁用用户")
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
  @PutMapping("unsealing/{id}")
  @ApiOperation(value = "根据id解封用户", notes = "根据id解封用户")
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
  @PostMapping("")
  @ApiOperation(value = "管理员添加用户", notes = "管理员添加用户")
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
  @PutMapping("")
  @ApiOperation(value = "管理员修改用户信息", notes = "管理管理员修改用户信息员添加用户")
  public Result<Boolean> updateUser(@RequestBody @Validated UserUpdateRequest userUpdateRequest) {
    log.info("开始执行修改用户信息逻辑,需要修改的用户的参数为：‘{}’", userUpdateRequest);
    return Result.of(userService.updateUser(userUpdateRequest));
  }

  /**
   * 开始校验注册邮件.
   *
   * @return
   * @author zeal
   * @date 2019/10/14 22:56
   */
  @PostMapping("confirm/email")
  public Result<Boolean> conformRegisterEmail(@RequestParam String email, @RequestParam String key) {
    log.info("开始校验用户{}的注册邮件信息", email);
    return Result.of(userService.confirmEmailRegister(email, key));
  }

  /**
   * 发送注册邮件.
   *
   * @author zhanglei
   * @date 2019-10-08  17:26
   */
  @PostMapping("register/email")
  public Result sendRegisterEmail(@RequestParam String username, @RequestParam String email) {
    log.info("开始执行发送注册邮件服务");
    try {
      emailService.sendRegisterEmail(username, email);
    } catch (UnsupportedEncodingException e) {
      log.error("发送注册邮件出错！出错信息为:{}", e.getMessage());
    }
    return Result.ok();
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
  @ApiOperation(value = "分页获取当前用户博客列表", notes = "分页获取当前用户博客列表")
  public Result<PageInfo<ArticleResponse>> getBlogByPaginate(@RequestParam(defaultValue = "1") Long pageNumber,
                                                             @RequestParam(defaultValue = "500") Long pageSize) {
    log.info("开始进行分页查询当前用户博客列表 ");
    Page<Article> articlePage = (Page<Article>) articleService
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
  @ApiOperation(value = "分页获取当前用户喜欢博客列表", notes = "分页获取当前用户喜欢博客列表")
  public Result<PageInfo<ArticleResponse>> getLikeBlogByPaginate(@RequestParam(defaultValue = "1") Long pageNumber,
                                                             @RequestParam(defaultValue = "500") Long pageSize) {
    log.info("开始进行分页查询当前用户喜欢的博客列表 ");
    Page<ArticleLike> articlePage = (Page<ArticleLike>) articleLikeService
            .page(new Page<>(pageNumber, pageSize), articleLikeHelper
                    .toCurrentUserLikeBlog());
    return Result
            .of(articleLikeHelper.toPageInfo(articlePage));
  }
}

