package com.zeal.zealsay.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.google.common.collect.Lists;
import com.zeal.zealsay.converter.ArticleCategoryConvertMapper;
import com.zeal.zealsay.dto.request.ArticleCategoryAddRequest;
import com.zeal.zealsay.dto.request.ArticleCategoryUpdateRequest;
import com.zeal.zealsay.dto.response.ArticleCategoryResponse;
import com.zeal.zealsay.entity.ArticleCategory;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.helper.ArticleCategoryHelper;
import com.zeal.zealsay.mapper.ArticleCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;

/**
 * <p>
 * 分类目录表 服务实现类
 * </p>
 *
 * @author zhanglei
 * @since 2018-12-29
 */
@Transactional(rollbackFor = {ServiceException.class,RuntimeException.class,Exception.class})
@Service
public class ArticleCategoryService extends AbstractService<ArticleCategoryMapper, ArticleCategory> implements IService<ArticleCategory> {

  @Autowired
  ArticleCategoryConvertMapper articleCategoryConvertMapper;
  @Autowired
  ArticleCategoryHelper articleCategoryHelper;
  /**
   * 添加文章分类目录.
   *
   * @author  zhanglei
   * @date 2018/12/29  5:07 PM
   */
  public Boolean addArticleCategory(ArticleCategoryAddRequest articleCategoryAddRequest) {
    ArticleCategory articleCategory = articleCategoryHelper
        .initBeforeAdd(articleCategoryAddRequest);
    //验重复
    checkCategoryRepeat(articleCategory);
    return save(articleCategory);
  }

  /**
   * 修改文章分类目录.
   *
   * @author  zhanglei
   * @date 2018/12/29  5:07 PM
   */
  public Boolean updateArticleCategory(ArticleCategoryUpdateRequest articleCategoryUpdateRequest) {
    ArticleCategory articleCategory = articleCategoryHelper
        .initBeforeUpdate(articleCategoryUpdateRequest);
    //验重复
    checkCategoryRepeat(articleCategory);
    return updateById(articleCategory);
  }

  /**
  * 获取分类目录列表.
  *
  * @author  zeal
  * @date 2019/4/14 21:51
  */
  @Async
  public Future<List<ArticleCategoryResponse>> getCategoryList() {
    List<ArticleCategoryResponse> categoryResponses = articleCategoryConvertMapper
            .toArticleCategoryResponseList(list(new QueryWrapper<>()));
    //递归设置子节点
    if (!CollectionUtils.isEmpty(categoryResponses)) {
      for(ArticleCategoryResponse categoryResponse:categoryResponses) {
        categoryResponse.setChildren(recursionChildren(categoryResponses,categoryResponse));
      }
    }
    return new AsyncResult<>(categoryResponses);
  }

  /**
  * 递归生成树.
  *
  * @author  zeal
  * @date 2019/4/14 21:49
  */
  private List<ArticleCategoryResponse> recursionChildren(List<ArticleCategoryResponse> categoryResponses,ArticleCategoryResponse category) {
    if (!CollectionUtils.isEmpty(categoryResponses) && Objects.nonNull(category)) {
      List<ArticleCategoryResponse> children = Lists.newArrayList();
      Iterator iterator = categoryResponses.iterator();
      while (iterator.hasNext()) {
        ArticleCategoryResponse categoryResponse = (ArticleCategoryResponse) iterator.next();
        if (category.getId().equals(categoryResponse.getParentId())) {
          categoryResponse.setChildren(recursionChildren(categoryResponses,categoryResponse));
          children.add(categoryResponse);
          iterator.remove();
        }
      }
      return children;
    }
    return null;
  }

  /**
   * 校验是否有重复的角色信息.
   *
   * @author  zeal
   * @date 2019/4/14 11:28
   */
  private void checkCategoryRepeat(ArticleCategory category) {
    QueryWrapper<ArticleCategory> queryWrapper = new QueryWrapper<ArticleCategory>()
            .and(wrapper -> wrapper.eq("name", category.getName())
                    .or()
                    .eq("alias", category.getAlias()));
    if (Objects.nonNull(category.getId())) {
      queryWrapper.ne("id",category.getId());
    }
    List<ArticleCategory  > articleCategories = list(queryWrapper);
    if (!CollectionUtils.isEmpty(articleCategories)) {
      throw new ServiceException("系统已存在相同分类信息！");
    }
  }
}
