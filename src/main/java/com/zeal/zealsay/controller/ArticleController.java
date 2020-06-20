package com.zeal.zealsay.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.converter.ArticleConvertMapper;
import com.zeal.zealsay.dto.request.ArticleAddRequest;
import com.zeal.zealsay.dto.request.ArticlePageRequest;
import com.zeal.zealsay.dto.request.ArticleUpdateRequest;
import com.zeal.zealsay.dto.response.ArticleResponse;
import com.zeal.zealsay.entity.Article;
import com.zeal.zealsay.helper.ArticleHelper;
import com.zeal.zealsay.service.ArticleLikeService;
import com.zeal.zealsay.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author zhanglei
 * @since 2018-11-28
 */
@Api(tags = "文章模块")
@Slf4j
@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {

  @Autowired
  ArticleService articleService;
  @Autowired
  ArticleLikeService articleLikeService;
  @Autowired
  ArticleConvertMapper articleConvertMapper;
  @Autowired
  ArticleHelper articleHelper;

  /**
   * 根据id来查询.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @GetMapping("/{id}")
  @ApiOperation(value = "根据id获取文章信息", notes = "根据id获取文章信息")
  public Result<ArticleResponse> getById(@PathVariable String id) {
    log.info("开始查询文章id为 '{}' 的文章信息", id);
    return Result
        .of(articleHelper.toArticleResponse(articleService.getById(id)));
  }

  /**
   * 分页查询.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @GetMapping("/page")
  @ApiOperation(value = "分页查询文章信息列表",notes = "分页查询文章信息列表")
  public Result<PageInfo<ArticleResponse>> getByPaginate(@RequestParam(defaultValue = "1") Long pageNumber,
                                                         @RequestParam(defaultValue = "10") Long pageSize,
                                                         ArticlePageRequest articlePageRequest) {
    log.info("开始进行分页查询文章列表，查询参数为 '{}' ", articlePageRequest);
    Page<Article> articlePage = (Page<Article>) articleService
        .page(new Page<>(pageNumber, pageSize), articleHelper.toArticlePageRequestWrapper(articlePageRequest));
    return Result.of(articleHelper.toPageInfo(articlePage));
  }

  /**
   * 分页查询.（博客端）
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @GetMapping("/c/page")
  @ApiOperation(value = "分页查询文章信息列表",notes = "分页查询文章信息列表")
  public Result<PageInfo<ArticleResponse>> getByPaginateByUser(@RequestParam(defaultValue = "1") Long pageNumber,
                                                         @RequestParam(defaultValue = "10") Long pageSize,
                                                         ArticlePageRequest articlePageRequest) {
    log.info("开始进行分页查询文章列表，查询参数为 '{}' ", articlePageRequest);
    Page<Article> articlePage = (Page<Article>) articleService
        .page(new Page<>(pageNumber, pageSize), articleHelper.toArticlePageRequestWrapperForC(articlePageRequest));
    return Result.of(articleHelper.toPageInfo(articlePage));
  }

  /**
   * 添加文章.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @PostMapping("")
  @ApiOperation(value = "文章添加", notes = "文章添加")
  public Result<Boolean> addArticle(@RequestBody ArticleAddRequest articleAddRequest) {
    log.info("开始添加文章，新增参数为 '{}' ", articleAddRequest);
    return Result
        .of(articleService.addArticle(articleAddRequest));
  }

  /**
   * 修改文章.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EDITOR')")
  @PutMapping("")
  @ApiOperation(value = "文章修改", notes = "文章修改")
  public Result<Boolean> updateArticle(@RequestBody ArticleUpdateRequest articleUpdateRequest) {
    log.info("开始修改文章，修改参数为 '{}' ", articleUpdateRequest);
    return Result
        .of(articleService.updateArticle(articleUpdateRequest));
  }

  /**
   * 批量下架文章作品.
   *
   * @author zhanglei
   * @date 2018/11/15  8:24 PM
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @PutMapping("down/batch")
  @ApiOperation(value = "根据id列表批量下架文章作品", notes = "根据id列表批量下架文章作品")
  public Result<Boolean> markArticleDownBatch(@RequestBody Collection<Long> ids) {
    log.info("开始执行对文章作品 id 在 '{}' 内的文章执行批量下架操作", ids.toString());
    return Result.of(articleService.markArticleDown(ids));
  }

  /**
   * 根据id下架文章作品.
   *
   * @author zhanglei
   * @date 2018/11/15  8:24 PM
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @PutMapping("down/{id}")
  @ApiOperation(value = "根据id下架文章作品", notes = "根据id下架文章作品")
  public Result<Boolean> markArticleDown(@PathVariable Long id) {
    log.info("开始执行对文章作品 id 为 '{}' 的文章执行下架操作", id);
    return Result.of(articleService.markArticleDown(id));
  }

  /**
   * 根据id上架文章作品.
   *
   * @author zhanglei
   * @date 2018/11/15  8:24 PM
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EDITOR')")
  @PutMapping("up/{id}")
  @ApiOperation(value = "根据id列表上架文章作品", notes = "根据id上架文章作品")
  public Result<Boolean> markArticleUp(@PathVariable Long id) {
    log.info("开始执行对文章作品 id 为 '{}' 的文章执行上架操作", id);
    return Result.of(articleService.markArticleUp(id));
  }

  /**
   * 根据id删除文章.
   *
   * @author  zhanglei
   * @date 2018/11/23  5:47 PM
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EDITOR')")
  @DeleteMapping("/{id}")
  @ApiOperation(value = "根据id删除文章信息",notes = "根据id删除文章信息")
  public Result<Boolean> deleteArticle(@PathVariable Long id) {
    log.info("开始删除id为 '{}' 的文章信息", id);
    return Result.of(articleService.removeById(id));
  }

  /**
   * 根据id列表批量删除文章.
   *
   * @author  zhanglei
   * @date 2018/11/23  5:47 PM
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @DeleteMapping("/batch")
  @ApiOperation(value = "根据id列表批量删除角色信息",notes = "根据id列表批量删除角色信息")
  public Result<Boolean> deleteArticleBatch(Collection<Long> ids) {
    log.info("开始批量删除id在 '{}' 的文章信息", ids.toString());
    return Result.of(articleService.removeByIds(ids));
  }

  /**
   * 根据id阅读文章.
   *
   * @author  zhanglei
   * @date 2018/11/23  5:47 PM
   */
  @GetMapping("/read/{id}")
  @ApiOperation(value = "根据id阅读文章",notes = "根据id阅读文章")
  public Result<Boolean> readArticle(@PathVariable Long id) {
    log.info("id为 '{}' 的文章信息阅读量加1", id);
    return Result.of(articleService.readArticle(id));
  }

  /**
   * 根据id查询是否喜欢过文章.
   *
   * @author  zhanglei
   * @date 2019-11-15  17:06
   */
  @GetMapping("/islike/{id}")
  @ApiOperation(value = "根据id查询是否喜欢过文章",notes = "根据id查询是否喜欢过文章")
  public Result<Boolean> islikeArticle(@PathVariable Long id) {
    log.info("查询id为 '{}' 的文章是否被喜欢过", id);
    return Result.of(articleLikeService.islike(id));
  }

  /**
   * 喜欢文章.
   *
   * @author  zhanglei
   * @date 2019-11-15  17:06
   */
  @GetMapping("/like/{id}")
  @ApiOperation(value = "根据id喜欢文章",notes = "根据id喜欢文章")
  public Result<Boolean> likeArticle(@PathVariable Long id) {
    log.info("id为 '{}' 的文章被喜欢", id);
    return Result.of(articleLikeService.like(id));
  }

  /**
   * 不喜欢文章.
   *
   * @author  zhanglei
   * @date 2019-11-15  17:06
   */
  @GetMapping("/dislike/{id}")
  @ApiOperation(value = "根据id取消喜欢文章",notes = "根据id取消喜欢文章")
  public Result<Boolean> dislikeArticle(@PathVariable Long id) {
    log.info("id为 '{}' 的文章被取消喜欢", id);
    return Result.of(articleLikeService.dislike(id));
  }
}

