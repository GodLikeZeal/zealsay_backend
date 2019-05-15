package com.zeal.zealsay.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.constant.enums.BlockAction;
import com.zeal.zealsay.common.constant.enums.BlockType;
import com.zeal.zealsay.common.constant.enums.UserStatus;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.converter.ArticleConvertMapper;
import com.zeal.zealsay.dto.request.ArticleAddRequest;
import com.zeal.zealsay.dto.request.ArticlePageRequest;
import com.zeal.zealsay.dto.request.ArticleUpdateRequest;
import com.zeal.zealsay.dto.response.ArticleResponse;
import com.zeal.zealsay.entity.Article;
import com.zeal.zealsay.entity.User;
import com.zeal.zealsay.helper.ArticleHelper;
import com.zeal.zealsay.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

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
        .of(articleConvertMapper.toArticleResponse(articleService.getById(id)));
  }

  /**
   * 分页查询.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @GetMapping("/page")
  @ApiOperation(value = "分页查询文章信息列表",notes = "分页查询文章信息列表")
  public Result<PageInfo<ArticleResponse>> getByPaginate(@Value("1") Long pageNumber,
                                                         @Value("10") Long pageSize,
                                                         ArticlePageRequest articlePageRequest) {
    log.info("开始进行分页查询文章列表，查询参数为 '{}' ", articlePageRequest);
    Page<Article> rolePage = (Page<Article>) articleService
        .page(new Page<>(pageNumber, pageSize), articleHelper.toAeticlePageRequestWrapper(articlePageRequest));
    return Result.of(articleHelper.toPageInfo(rolePage));
  }

  /**
   * 添加文章.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @PostMapping("")
  @ApiOperation(value = "文章添加", notes = "根据id获取文章信息")
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
  @PutMapping("")
  @ApiOperation(value = "文章修改", notes = "根据id修改文章信息")
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
  @DeleteMapping("/batch")
  @ApiOperation(value = "根据id列表批量删除角色信息",notes = "根据id列表批量删除角色信息")
  public Result<Boolean> deleteArticleBatch(Collection<Long> ids) {
    log.info("开始批量删除id在 '{}' 的文章信息", ids.toString());
    return Result.of(articleService.removeByIds(ids));
  }
}

