package com.zeal.zealsay.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.dto.request.CommentRequest;
import com.zeal.zealsay.dto.response.CommentResponse;
import com.zeal.zealsay.entity.Comment;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.helper.CommentHelper;
import com.zeal.zealsay.mapper.CommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 评论模块服务.
 *
 * @author zhanglei
 * @date 2020/6/16  5:03 下午
 */
@Slf4j
@Transactional(rollbackFor = {ServiceException.class, RuntimeException.class, Exception.class})
@Service
public class CommentService extends AbstractService<CommentMapper, Comment> implements IService<Comment> {

    @Autowired
    CommentHelper commentHelper;

    /**
     * 发表评论.
     *
     * @author zhanglei
     * @date 2020/6/16 21:13
     */
    public Boolean createComment(CommentRequest commentRequest) {

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentRequest, comment);
        comment.setCreateDate(LocalDateTime.now());
        comment.setIsDel(false);
        comment.setLikeNum(0);
        return save(comment);
    }

    /**
     * 根据文章id查询.
     *
     * @author zhanglei
     * @date 2020/6/16  5:24 下午
     */
    public PageInfo<CommentResponse> pageCommentList(Long pageNumber, Long pageSize, Long articleId, Long commentId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_date");
        wrapper.eq("article_id", articleId);

        if (Objects.nonNull(commentId)) {
            //查询楼中回复
            wrapper.eq("comment_id", commentId);
        } else {
            //查询总回复
            wrapper.isNull("comment_id");
        }
        Page<Comment> page = page(new Page<>(pageNumber, pageSize), wrapper);
        return commentHelper.toPageInfo(page);
    }

    /**
     * 近期评论.
     *
     * @author zhanglei
     * @date 2020/6/18  4:31 下午
     */
    public List<CommentResponse> recentDiscuss() {
        List<Comment> comments = list(new QueryWrapper<Comment>().orderByDesc("create_date").last("limit 10"));
        if (CollectionUtils.isEmpty(comments)) {
            return null;
        }

        return comments.stream().map(c -> {
            CommentResponse response = new CommentResponse();
            BeanUtils.copyProperties(c, response);
            return response;
        }).collect(Collectors.toList());
    }

    /**
     * 点赞评论.
     *
     * @author zhanglei
     * @date 2020/6/17  5:52 下午
     */
    public Boolean thumbUp(Long id) {
        Comment comment = getById(id);
        if (Objects.isNull(comment)) {
            throw new ServiceException("评论参数有误");
        }
        return updateById(Comment.builder()
                .id(id)
                .likeNum(comment.getLikeNum() + 1)
                .build());
    }

    /**
     * 取消点赞评论.
     *
     * @author zhanglei
     * @date 2020/6/17  5:52 下午
     */
    public Boolean thumbDown(Long id) {
        Comment comment = getById(id);
        if (Objects.isNull(comment)) {
            throw new ServiceException("评论参数有误");
        }
        int num = Math.max(comment.getLikeNum() - 1, 0);
        return updateById(Comment.builder()
                .id(id)
                .likeNum(num)
                .build());
    }
}
