package com.zeal.zealsay.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.dto.response.CommentResponse;
import com.zeal.zealsay.entity.Comment;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.mapper.CommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    return null;
  }
}
