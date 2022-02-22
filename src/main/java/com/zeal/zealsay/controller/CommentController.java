package com.zeal.zealsay.controller;


import com.zeal.zealsay.common.annotation.DuplicateSubmit;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.dto.request.CommentRequest;
import com.zeal.zealsay.dto.response.ArticleResponse;
import com.zeal.zealsay.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.zeal.zealsay.common.annotation.DuplicateSubmit.SESSION;


/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author zhanglei
 * @since 2018-11-28
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    /**
     * 根据id来查询.
     *
     * @author zhanglei
     * @date 2018/9/7  下午6:00
     */
    @DuplicateSubmit(type = SESSION)
    @PostMapping("")
    public Result<Boolean> createComment(@RequestBody CommentRequest commentRequest) {
        log.info("{}对文章'{}'发表了评论'{}'", commentRequest.getFromName(),
                commentRequest.getArticleTitle(), commentRequest.getContent());
        return Result.of(commentService.createComment(commentRequest));
    }

    /**
     * 分页查询.
     *
     * @author zhanglei
     * @date 2018/9/7  下午6:00
     */
    @GetMapping("/page")
    public Result<PageInfo<ArticleResponse>> getByPaginate(@RequestParam(defaultValue = "1") Long pageNumber,
                                                           @RequestParam(defaultValue = "10") Long pageSize,
                                                           @RequestParam(required = false) Long commentId,
                                                           @RequestParam Long articleId) {
        log.info("开始进行分页查询列表");
        return Result.of(commentService.pageCommentList(pageNumber, pageSize, articleId,commentId));
    }

    /**
     * 点赞动作.
     *
     * @author  zhanglei
     * @date 2020/6/17  5:42 下午
     */
    @DuplicateSubmit
    @GetMapping("/thumb/up/{id}")
    public Result<Boolean> thumbUp(@PathVariable Long id) {
        log.info("id为 '{}' 的评论被赞了一下", id);
        return Result.of(commentService.thumbUp(id));
    }

    /**
     * 取消点赞动作.
     *
     * @author  zhanglei
     * @date 2020/6/17  5:42 下午
     */
    @DuplicateSubmit
    @GetMapping("/thumb/down/{id}")
    public Result<Boolean> thumbDown(@PathVariable Long id) {
        log.info("id为 '{}' 的评论被取消赞一次", id);
        return Result.of(commentService.thumbDown(id));
    }
}

