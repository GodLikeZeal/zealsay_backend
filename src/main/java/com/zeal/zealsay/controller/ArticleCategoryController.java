package com.zeal.zealsay.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.dto.request.ArticleCategoryAddRequest;
import com.zeal.zealsay.dto.request.ArticleCategoryPageRequest;
import com.zeal.zealsay.dto.request.ArticleCategoryUpdateRequest;
import com.zeal.zealsay.dto.response.ArticleCategoryResponse;
import com.zeal.zealsay.entity.ArticleCategory;
import com.zeal.zealsay.helper.ArticleCategoryHelper;
import com.zeal.zealsay.service.ArticleCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 * 分类目录表 前端控制器
 * </p>
 *
 * @author zhanglei
 * @since 2018-12-29
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/article/category")
public class ArticleCategoryController {

    @Autowired
    ArticleCategoryService articleCategoryService;
    @Autowired
    ArticleCategoryHelper articleCategoryHelper;

    /**
     * 根据id来查询.
     *
     * @author zhanglei
     * @date 2018/9/7  下午6:00
     */
    @GetMapping("/{id}")
    public Result<ArticleCategoryResponse> getById(@PathVariable String id) {
        log.info("开始查询分类目录id为 '{}' 的分类目录信息", id);
        ArticleCategory category = articleCategoryService.getById(id);
        if (Objects.isNull(category)) {
            return Result.of(null);
        }
        ArticleCategoryResponse response = new ArticleCategoryResponse();
        BeanUtils.copyProperties(category, response);
        return Result.of(response);
    }

    /**
     * 根据id来查询.
     *
     * @author zhanglei
     * @date 2018/9/7  下午6:00
     */
    @GetMapping("")
    public Result<List<ArticleCategoryResponse>> getCategoryList() throws ExecutionException, InterruptedException {
        log.info("开始查询分类目录列表");
        return Result.of(articleCategoryService.getCategoryList().get());
    }

    /**
     * 分页查询.
     *
     * @author zhanglei
     * @date 2018/9/7  下午6:00
     */
    @GetMapping("/page")
    public Result<PageInfo<ArticleCategoryResponse>> getByPaginate(@RequestParam(defaultValue = "1") Long pageNumber,
                                                                   @RequestParam(defaultValue = "10") Long pageSize,
                                                                   ArticleCategoryPageRequest articlePageRequest) {
        log.info("开始进行分页查询分类目录列表，查询参数为 '{}' ", articlePageRequest);
        ArticleCategory articleCategory = new ArticleCategory();
        BeanUtils.copyProperties(articlePageRequest,articleCategory);
        Page<ArticleCategory> rolePage = (Page<ArticleCategory>) articleCategoryService
                .page(new Page<>(pageNumber, pageSize), new QueryWrapper(articleCategory));
        return Result.of(articleCategoryHelper.toPageInfo(rolePage));
    }

    /**
     * 添加分类目录.
     *
     * @author zhanglei
     * @date 2018/9/7  下午6:00
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("")
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("")
    public Result<Boolean> updateArticle(@RequestBody @Validated ArticleCategoryUpdateRequest articleCategoryUpdateRequest) {
        log.info("开始修改分类目录，修改参数为 '{}' ", articleCategoryUpdateRequest);
        return Result
                .of(articleCategoryService.updateArticleCategory(articleCategoryUpdateRequest));
    }

    /**
     * 根据id删除分类目录.
     *
     * @author zhanglei
     * @date 2018/11/23  5:47 PM
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteArticle(@PathVariable Long id) {
        log.info("开始删除id为 '{}' 的分类目录信息", id);
        return Result.of(articleCategoryService.removeById(id));
    }

    /**
     * 根据id列表批量删除分类目录.
     *
     * @author zhanglei
     * @date 2018/11/23  5:47 PM
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/batch")
    public Result<Boolean> deleteArticleBatch(@RequestBody Collection<Long> ids) {
        log.info("开始批量删除id在 '{}' 的分类目录信息", ids.toString());
        return Result.of(articleCategoryService.removeByIds(ids));
    }
}

