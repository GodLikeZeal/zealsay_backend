package com.zeal.zealsay.converter;

import com.zeal.zealsay.dto.response.CommentResponse;
import com.zeal.zealsay.entity.Comment;
import org.mapstruct.Mapper;


/**
 * role相关转换器.
 *
 * @author  zhanglei
 * @date 2018/11/15  5:43 PM
 */
@Mapper(componentModel = "spring")
public interface CommentConvertMapper {

  CommentResponse toCommentResponse(Comment comment);


}
