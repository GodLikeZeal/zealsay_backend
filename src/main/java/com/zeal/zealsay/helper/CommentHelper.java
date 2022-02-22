package com.zeal.zealsay.helper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.dto.response.CommentResponse;
import com.zeal.zealsay.entity.Comment;
import com.zeal.zealsay.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
    CommentService commentService;


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
        CommentResponse commentResponse = new CommentResponse();
        BeanUtils.copyProperties(c, commentResponse);
        commentResponse.setInputText(false);
        Page<Comment> page = commentService.page(new Page<>(1, 5), new QueryWrapper<Comment>()
                .eq("comment_id", c.getId()));
        List<Comment> list = page.getRecords();
        List<CommentResponse> responses = null;
        if (!CollectionUtils.isEmpty(list)) {
            responses = list.stream().map(r -> {
                CommentResponse response = new CommentResponse();
                BeanUtils.copyProperties(r, response);
                response.setInputText(false);
                return response;
            }).collect(Collectors.toList());
        }
        PageInfo<CommentResponse> responsePageInfo = toPageInfo(page);
        responsePageInfo.setRecords(responses);
        commentResponse.setReplys(responsePageInfo);
        return commentResponse;
    }


}
