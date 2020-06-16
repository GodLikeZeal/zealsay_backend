package com.zeal.zealsay.controller;


import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.dto.request.CommentRequest;
import com.zeal.zealsay.dto.response.ArticleResponse;
import com.zeal.zealsay.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author zhanglei
 * @since 2018-11-28
 */
@Api(tags = "文章模块")
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
    @PostMapping("")
    @ApiOperation(value = "添加评论", notes = "添加评论")
    public Result<Boolean> createComment(@RequestBody CommentRequest commentRequest) {
        log.info("{}对文章'{}'发表了评论'{}'", commentRequest.getFromName(),
                commentRequest.getArticleName(), commentRequest.getContent());
        return Result
                .of(commentService.createComment(commentRequest));
    }

    /**
     * 分页查询.
     *
     * @author zhanglei
     * @date 2018/9/7  下午6:00
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询评论", notes = "分页查询评论")
    public Result<PageInfo<ArticleResponse>> getByPaginate(@RequestParam(defaultValue = "1") Long pageNumber,
                                                           @RequestParam(defaultValue = "10") Long pageSize,
                                                           @RequestParam Long articleId) {
        log.info("开始进行分页查询列表");
        return Result.of(commentService.pageCommentList(pageNumber, pageSize, articleId));
    }
}

