package com.zeal.zealsay.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.converter.CommentConvertMapper;
import com.zeal.zealsay.dto.request.CommentRequest;
import com.zeal.zealsay.dto.response.CommentResponse;
import com.zeal.zealsay.entity.Comment;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.helper.CommentHelper;
import com.zeal.zealsay.mapper.CommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;


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
  @Autowired
  CommentConvertMapper commentConvertMapper;

  /**
   * 发表评论.
   *
   * @author zhanglei
   * @date 2020/6/16 21:13
   */
  public Boolean createComment(CommentRequest commentRequest) {
    Comment comment = commentConvertMapper.toComment(commentRequest);
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
  public PageInfo<CommentResponse> pageCommentList(Long pageNumber, Long pageSize, Long articleId) {
    QueryWrapper<Comment> wrapper = new QueryWrapper<>();
    wrapper.orderByDesc("create_date");
    wrapper.eq("article_id", articleId);
    Page<Comment> page = page(new Page<>(pageNumber, pageSize), wrapper);
    return commentHelper.toPageInfo(page);
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
