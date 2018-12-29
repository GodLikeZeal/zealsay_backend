package com.zeal.zealsay.converter;

import com.zeal.zealsay.dto.request.*;
import com.zeal.zealsay.dto.response.ArticleResponse;
import com.zeal.zealsay.entity.Article;
import com.zeal.zealsay.entity.Role;
import org.mapstruct.Mapper;

/**
 * role相关转换器.
 *
 * @author  zhanglei
 * @date 2018/11/15  5:43 PM
 */
@Mapper(componentModel = "spring")
public interface ArticleConvertMapper {

  ArticleResponse toArticleResponse(Article article);

  Article toArticle(ArticleAddRequest articleAddRequest);

  Article toArticle(ArticleUpdateRequest roleAddResquest);

  Article toArticle(ArticlePageRequest articlePageRequest);

}
