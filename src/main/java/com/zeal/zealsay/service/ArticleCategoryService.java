package com.zeal.zealsay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeal.zealsay.converter.ArticleCategoryConvertMapper;
import com.zeal.zealsay.dto.request.ArticleCategoryAddRequest;
import com.zeal.zealsay.dto.request.ArticleCategoryUpdateRequest;
import com.zeal.zealsay.entity.ArticleCategory;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.helper.ArticleCategoryHelper;
import com.zeal.zealsay.mapper.ArticleCategoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class ArticleCategoryService extends ServiceImpl<ArticleCategoryMapper, ArticleCategory> implements IService<ArticleCategory> {

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
    return updateById(articleCategory);
  }
}
