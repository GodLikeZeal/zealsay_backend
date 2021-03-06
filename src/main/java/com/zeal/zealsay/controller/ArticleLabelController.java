package com.zeal.zealsay.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.dto.request.ArticleLabelAddRequest;
import com.zeal.zealsay.dto.request.ArticleLabelUpdateRequest;
import com.zeal.zealsay.dto.response.ArticleLabelResponse;
import com.zeal.zealsay.entity.ArticleLabel;
import com.zeal.zealsay.helper.ArticleLabelHelper;
import com.zeal.zealsay.service.ArticleLabelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
* 标签云 控制器.
*
* @author  zeal
* @date 2019/5/19 23:07
*/
@Api(tags = "标签模块")
@Slf4j
@RestController
@RequestMapping("/api/v1/article/label")
public class ArticleLabelController {

    @Autowired
    ArticleLabelService articleLabelService;
    @Autowired
    ArticleLabelHelper articleLabelHelper;

    /**
    * 标签云添加.
    *
    * @author  zeal
    * @date 2019/5/19 23:35
    */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("")
    @ApiOperation(value = "标签云添加", notes = "新增加标签云")
    public Result<Boolean> addArticleLabel(@RequestBody @Validated ArticleLabelAddRequest articleLabelAddRequest) {
        log.info("开始执行添加标签云逻辑,新增加标签云的参数为：‘{}’", articleLabelAddRequest);
        return Result.of(articleLabelService.addArticleLabel(articleLabelAddRequest));
    }

    /**
    * 标签云修改.
    *
    * @author  zeal
    * @date 2019/5/19 23:35
    */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("")
    @ApiOperation(value = "标签云修改", notes = "修改标签云")
    public Result<Boolean> updateArticleLabel(@RequestBody @Validated ArticleLabelUpdateRequest articleLabelUpdateRequest) {
        log.info("开始执行修改标签云逻辑,修改标签云的参数为：‘{}’", articleLabelUpdateRequest);
        return Result.of(articleLabelService.updateArticleLabel(articleLabelUpdateRequest));
    }

    /**
    * 分页获取标签云信息列表.
    *
    * @author  zeal
    * @date 2019/5/19 23:47
    */
    @GetMapping("/page")
    @ApiOperation(value = "分页获取标签云信息列表", notes = "分页获取标签云信息列表")
    public Result<PageInfo<ArticleLabelResponse>> getByPaginate(@RequestParam(defaultValue = "1") Long pageNumber,
                                                                @RequestParam(defaultValue = "500") Long pageSize,
                                                                String name) {
        log.info("开始进行分页查询标签云列表，查询参数为 '{}' ", name);
        Page<ArticleLabel> articleLabelPage = (Page<ArticleLabel>) articleLabelService
                .page(new Page<>(pageNumber, pageSize), articleLabelHelper
                        .buildVagueQuery(name));
        return Result
                .of(articleLabelHelper.toPageInfo(articleLabelPage));
    }

    /**
    * 标签云删除.
    *
    * @author  zeal
    * @date 2019/5/19 23:49
    */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "标签云删除", notes = "标签云删除")
    public Result<Boolean> deleteArticleLabel(@PathVariable("id") Long id) {
        log.info("开始执行删除标签云逻辑,删除标签云的id为：‘{}’", id);
        return Result.of(articleLabelService.removeById(id));
    }

    /**
    * 获取标签列表.
    *
    * @author  zeal
    * @date 2019/5/25 21:51
    */
    @GetMapping("")
    @ApiOperation(value = "获取标签列表", notes = "获取标签列表")
    public Result<List<ArticleLabelResponse>> getArticleLabelList() {
        log.info("开始查询标签云列表");
        List<ArticleLabel> labels = articleLabelService.list(new QueryWrapper<>());
        return Result.of(articleLabelHelper.toArticleLabelResponseList(labels));
    }
}

