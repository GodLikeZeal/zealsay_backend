package com.zeal.zealsay.helper;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.converter.CommentConvertMapper;
import com.zeal.zealsay.dto.response.CommentResponse;
import com.zeal.zealsay.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文章帮助类.
 *
 * @author zhanglei
 * @date 2018/11/15  6:53 PM
 */
@Component
public class CommentHelper {

  @Autowired
  CommentConvertMapper commentConvertMapper;


  /**
   * 转换成返回列表.
   *
   * @param commentPage 分页
   * @author zhanglei
   * @date 2018/11/15  9:25 PM
   */
  public PageInfo<CommentResponse> toPageInfo(Page<Comment> commentPage) {
    PageInfo<Comment> commentPageInfo = new PageInfo(commentPage);
    List<CommentResponse> commentResponses = commentPageInfo.getRecords()
        .stream()
        .map(this::applyPage)
        .collect(Collectors.toList());
    return PageInfo.<CommentResponse>builder()
        .records(commentResponses)
        .currentPage(commentPageInfo.getCurrentPage())
        .pageSize(commentPageInfo.getPageSize())
        .total(commentPageInfo.getTotal())
        .build();
  }

  /**
   * 解析返回信息.
   *
   * @author zhanglei
   * @date 2019-07-29  18:22
   */
  private CommentResponse applyPage(Comment c) {
    CommentResponse commentResponse= commentConvertMapper.toCommentResponse(c);
    commentResponse.setInputText(false);
    return commentResponse;
  }


}
