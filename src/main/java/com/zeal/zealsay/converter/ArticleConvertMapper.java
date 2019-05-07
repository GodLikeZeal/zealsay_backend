package com.zeal.zealsay.converter;

import com.zeal.zealsay.dto.request.*;
import com.zeal.zealsay.dto.response.ArticleResponse;
import com.zeal.zealsay.entity.Article;
import org.mapstruct.Mapper;
import org.springframework.util.CollectionUtils;

import java.util.List;

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

  default String toLabel(List<String> label){
    if (CollectionUtils.isEmpty(label)) {
      return null;
    }
    StringBuffer sb = new StringBuffer();
    for (String s:label) {
      sb.append(s).append(",");
    }
    sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }

}
