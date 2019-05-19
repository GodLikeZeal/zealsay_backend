package com.zeal.zealsay.helper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.converter.ArticleLabelConvertMapper;
import com.zeal.zealsay.converter.RoleConvertMapper;
import com.zeal.zealsay.dto.request.ArticleLabelAddRequest;
import com.zeal.zealsay.dto.request.ArticleLabelUpdateRequest;
import com.zeal.zealsay.dto.request.RoleAddResquest;
import com.zeal.zealsay.dto.request.RoleUpdateRequest;
import com.zeal.zealsay.dto.response.ArticleLabelResponse;
import com.zeal.zealsay.dto.response.RoleResponse;
import com.zeal.zealsay.entity.ArticleLabel;
import com.zeal.zealsay.entity.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 标签云帮助类.
 *
 * @author  zhanglei
 * @date 2018/11/15  6:53 PM
 */
@Component
public class ArticleLabelHelper {

  @Autowired
  ArticleLabelConvertMapper articleLabelConvertMapper;

  /**
   * 更新之前通过请求参数转换成Role.
   *
   * @author  zhanglei
   * @date 2018/11/15  7:46 PM
   */
  public ArticleLabel initBeforeUpdate(ArticleLabelUpdateRequest articleLabelUpdateRequest){
    ArticleLabel articleLabel = articleLabelConvertMapper.toArticleLabel(articleLabelUpdateRequest);
    return articleLabel;
  }

  /**
   * 添加之前通过请求参数转换成ArticleLabel.
   *
   * @author  zhanglei
   * @date 2018/11/15  7:46 PM
   */
  public ArticleLabel initBeforeAdd(ArticleLabelAddRequest articleLabelAddRequest){
    ArticleLabel articleLabel = articleLabelConvertMapper.toArticleLabel(articleLabelAddRequest);
    return articleLabel;
  }

  /**
  * 构造分页查询参数.
  *
  * @author  zeal
  * @date 2019/5/19 23:43
  */
  public QueryWrapper buildVagueQuery(String name) {
    QueryWrapper<ArticleLabel> queryWrapper = new QueryWrapper<>();

    if (StringUtils.isNotBlank(name)) {
      queryWrapper.lambda().like(ArticleLabel::getName,name);
    }

    return queryWrapper;
  }

  /**
   * 转换成返回列表.
   *
   * @author  zhanglei
   * @date 2018/11/15  9:25 PM
   * @param articleLabelPage
   */
  public PageInfo<ArticleLabelResponse> toPageInfo(Page<ArticleLabel> articleLabelPage){
    PageInfo<ArticleLabel> articleLabelInfo = new PageInfo(articleLabelPage);
    List<ArticleLabelResponse> articleLabelResponses = articleLabelInfo.getRecords()
        .stream()
        .map(s -> articleLabelConvertMapper.toArticleLabelResponse(s))
        .collect(Collectors.toList());
    return PageInfo.<ArticleLabelResponse>builder()
        .records(articleLabelResponses)
        .currentPage(articleLabelInfo.getCurrentPage())
        .pageSize(articleLabelInfo.getPageSize())
        .total(articleLabelInfo.getTotal())
        .build();
  }
}
