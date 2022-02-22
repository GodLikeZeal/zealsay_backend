package com.zeal.zealsay.helper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.entity.PageInfo;

import com.zeal.zealsay.dto.request.ArticleCategoryAddRequest;
import com.zeal.zealsay.dto.request.ArticleCategoryUpdateRequest;
import com.zeal.zealsay.dto.response.ArticleCategoryResponse;
import com.zeal.zealsay.entity.ArticleCategory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色帮助类.
 *
 * @author zhanglei
 * @date 2018/11/15  6:53 PM
 */
@Component
public class ArticleCategoryHelper {

    /**
     * 更新之前通过请求参数转换成ArticleCategory.
     *
     * @author zhanglei
     * @date 2018/11/15  7:46 PM
     */
    public ArticleCategory initBeforeUpdate(ArticleCategoryUpdateRequest articleCategoryUpdateRequest) {

        ArticleCategory articleCategory = new ArticleCategory();
        BeanUtils.copyProperties(articleCategoryUpdateRequest, articleCategory);
        return articleCategory;
    }

    /**
     * 添加之前通过请求参数转换成ArticleCategory.
     *
     * @author zhanglei
     * @date 2018/11/15  7:46 PM
     */
    public ArticleCategory initBeforeAdd(ArticleCategoryAddRequest articleCategoryAddRequest) {
        ArticleCategory articleCategory = new ArticleCategory();
        BeanUtils.copyProperties(articleCategoryAddRequest, articleCategory);
        return articleCategory;
    }

    /**
     * 转换成返回列表.
     *
     * @param articleCategoryPage
     * @author zhanglei
     * @date 2018/11/15  9:25 PM
     */
    public PageInfo<ArticleCategoryResponse> toPageInfo(Page<ArticleCategory> articleCategoryPage) {
        PageInfo<ArticleCategory> articleCategoryPageInfo = new PageInfo(articleCategoryPage);
        List<ArticleCategoryResponse> articleCategoryResponses = articleCategoryPage.getRecords()
                .stream()
                .map(s -> {
                    ArticleCategoryResponse response = new ArticleCategoryResponse();
                    BeanUtils.copyProperties(s,response);
                    return response;
                })
                .collect(Collectors.toList());
        return PageInfo.<ArticleCategoryResponse>builder()
                .records(articleCategoryResponses)
                .currentPage(articleCategoryPageInfo.getCurrentPage())
                .pageSize(articleCategoryPageInfo.getPageSize())
                .total(articleCategoryPage.getTotal())
                .build();
    }
}
