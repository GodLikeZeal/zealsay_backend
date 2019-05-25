package com.zeal.zealsay.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zeal.zealsay.converter.ArticleLabelConvertMapper;
import com.zeal.zealsay.dto.request.ArticleLabelAddRequest;
import com.zeal.zealsay.dto.request.ArticleLabelUpdateRequest;
import com.zeal.zealsay.dto.request.ArticleUpdateRequest;
import com.zeal.zealsay.entity.ArticleLabel;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.helper.ArticleLabelHelper;
import com.zeal.zealsay.mapper.ArticleLabelMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 分类目录表 服务实现类
 * </p>
 *
 * @author zhanglei
 * @since 2019-05-16
 */
@Service
public class ArticleLabelService extends ServiceImpl<ArticleLabelMapper, ArticleLabel> {

    @Autowired
    ArticleLabelHelper articleLabelHelper;

    /**
    * 新增标签云.
    *
    * @author  zeal
    * @date 2019/5/19 23:29
    */
    public Boolean addArticleLabel(ArticleLabelAddRequest articleLabelAddRequest) {
        ArticleLabel articleLabel =  articleLabelHelper.initBeforeAdd(articleLabelAddRequest);
        //验证是否重复
        List<ArticleLabel> articleLabels = list(new QueryWrapper<ArticleLabel>()
                .lambda()
                .eq(ArticleLabel::getName,articleLabel.getName()));
        if (!CollectionUtils.isEmpty(articleLabels)) {
            throw new ServiceException("这个标签云已经有了，请不要重复添加哟");
        }

        return save(articleLabel);
    }

    /**
    * 修改标签云.
    *
    * @author  zeal
    * @date 2019/5/19 23:33
    */
    public Boolean updateArticleLabel(ArticleLabelUpdateRequest articleLabelUpdateRequest) {
        ArticleLabel articleLabel =  articleLabelHelper.initBeforeUpdate(articleLabelUpdateRequest);
        //验证是否重复
        List<ArticleLabel> articleLabels = list(new QueryWrapper<ArticleLabel>()
                .lambda()
                .eq(ArticleLabel::getId,articleLabel.getId())
                .eq(ArticleLabel::getName,articleLabel.getName()));
        if (!CollectionUtils.isEmpty(articleLabels)) {
            throw new ServiceException("这个标签云已经有了，请换一个标签名称");
        }

        return save(articleLabel);
    }

}
