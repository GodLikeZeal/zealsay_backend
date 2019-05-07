package com.zeal.zealsay.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.converter.ArticleCategoryConvertMapper;
import com.zeal.zealsay.dto.request.ArticleCategoryAddRequest;
import com.zeal.zealsay.dto.request.ArticleCategoryPageRequest;
import com.zeal.zealsay.dto.request.ArticleCategoryUpdateRequest;
import com.zeal.zealsay.dto.response.ArticleCategoryResponse;
import com.zeal.zealsay.entity.ArticleCategory;
import com.zeal.zealsay.helper.ArticleCategoryHelper;
import com.zeal.zealsay.service.ArticleCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 分类目录表 前端控制器
 * </p>
 *
 * @author zhanglei
 * @since 2018-12-29
 */
@Api(tags = "角色模块")
@Slf4j
@RestController
@RequestMapping("/api/v1/articleCategory")
public class ArticleCategoryController {

  @Autowired
  ArticleCategoryService articleCategoryService;
  @Autowired
  ArticleCategoryHelper articleCategoryHelper;
  @Autowired
  ArticleCategoryConvertMapper articleCategoryConvertMapper;

  /**
   * 根据id来查询.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @GetMapping("/{id}")
  @ApiOperation(value = "根据id获取分类目录信息", notes = "根据id获取分类目录信息")
  public Result<ArticleCategoryResponse> getById(@PathVariable String id) {
    log.info("开始查询分类目录id为 '{}' 的分类目录信息", id);
    return Result
        .of(articleCategoryConvertMapper.toArticleCategoryResponse(articleCategoryService.getById(id)));
  }

  /**
   * 根据id来查询.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @GetMapping("")
  @ApiOperation(value = "获取分类目录信息", notes = "获取分类目录信息")
  public Result<List<ArticleCategoryResponse>> getCategoryList() {
    log.info("开始查询分类目录列表");
    return Result.of(articleCategoryService.getCategoryList());
  }

  /**
   * 分页查询.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @GetMapping("/page")
  @ApiOperation(value = "分页查询分类目录信息列表",notes = "分页查询分类目录信息列表")
  public Result<PageInfo<ArticleCategoryResponse>> getByPaginate(@Value("1") Long pageNumber,
                                                                 @Value("10") Long pageSize,
                                                                 ArticleCategoryPageRequest articlePageRequest) {
    log.info("开始进行分页查询分类目录列表，查询参数为 '{}' ", articlePageRequest);
    Page<ArticleCategory> rolePage = (Page<ArticleCategory>) articleCategoryService
        .page(new Page<>(pageNumber, pageSize), new QueryWrapper(articleCategoryConvertMapper
            .toArticleCategory(articlePageRequest)));
    return Result.of(articleCategoryHelper.toPageInfo(rolePage));
  }

  /**
   * 添加分类目录.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @PostMapping("")
  @ApiOperation(value = "分类目录添加", notes = "分类目录添加")
  public Result<Boolean> addArticleCategory(@RequestBody @Validated ArticleCategoryAddRequest articleCategoryAddRequest) {
    log.info("开始添加分类目录，新增参数为 '{}' ", articleCategoryAddRequest);
    return Result
        .of(articleCategoryService.addArticleCategory(articleCategoryAddRequest));
  }

  /**
   * 修改分类目录.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @PutMapping("")
  @ApiOperation(value = "分类目录修改", notes = "分类目录修改")
  public Result<Boolean> updateArticle(@RequestBody @Validated ArticleCategoryUpdateRequest articleCategoryUpdateRequest) {
    log.info("开始修改分类目录，修改参数为 '{}' ", articleCategoryUpdateRequest);
    return Result
        .of(articleCategoryService.updateArticleCategory(articleCategoryUpdateRequest));
  }

  /**
   * 根据id删除分类目录.
   *
   * @author  zhanglei
   * @date 2018/11/23  5:47 PM
   */
  @DeleteMapping("/{id}")
  @ApiOperation(value = "根据id删除分类目录信息",notes = "根据id删除分类目录信息")
  public Result<Boolean> deleteArticle(@PathVariable Long id) {
    log.info("开始删除id为 '{}' 的分类目录信息", id);
    return Result.of(articleCategoryService.removeById(id));
  }

  /**
   * 根据id列表批量删除分类目录.
   *
   * @author  zhanglei
   * @date 2018/11/23  5:47 PM
   */
  @DeleteMapping("/batch")
  @ApiOperation(value = "根据id列表批量删除角色信息",notes = "根据id列表批量删除角色信息")
  public Result<Boolean> deleteArticleBatch(@RequestBody Collection<Long> ids) {
    log.info("开始批量删除id在 '{}' 的分类目录信息", ids.toString());
    return Result.of(articleCategoryService.removeByIds(ids));
  }
}

