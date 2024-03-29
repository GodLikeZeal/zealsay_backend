package com.zeal.zealsay.common.entity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 分页封装.
 *
 * @author zhanglei
 * @date 2018/9/20  下午8:40
 */
@Data
@Builder
@AllArgsConstructor
public class PageInfo<T> {

  public static final Long DEFAULT_PAGE_SIZE = 10L;
  public static final Long DEFAULT_PAGE_NUMBER = 1L;

  private Long pageSize;
  private Long currentPage;
  private Long total;

  @Builder.Default
  private List<T> records = Collections.emptyList();

  /**
   * 构造器.
   *
   * @author zhanglei
   * @date 2018/9/21  上午10:50
   */
  public PageInfo(Page<T> page) {
    this.currentPage = page.getCurrent();
    this.pageSize = page.getSize();
    this.total = page.getTotal();
    this.records = page.getRecords();
  }

  /**
   * 构造器.
   *
   * @author zhanglei
   * @date 2018/9/21  下午5:20
   */
  public PageInfo() {

  }
  /**
   * 构造器.
   *
   * @author zhanglei
   * @date 2018/9/21  下午5:20
   */
  public PageInfo(Long currentPage, Long pageSize) {
    if (Objects.nonNull(currentPage)) {
      this.currentPage = currentPage;
    } else {
      this.currentPage = DEFAULT_PAGE_NUMBER;
    }
    if (Objects.nonNull(pageSize)) {
      this.pageSize = pageSize;
    } else {
      this.pageSize = DEFAULT_PAGE_SIZE;
    }
  }
}
