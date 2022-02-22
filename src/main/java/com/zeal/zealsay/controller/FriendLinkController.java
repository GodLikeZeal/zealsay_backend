package com.zeal.zealsay.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.dto.request.FriendLinkRequest;
import com.zeal.zealsay.entity.FriendLink;
import com.zeal.zealsay.helper.FriendLinkHelper;
import com.zeal.zealsay.service.FriendLinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * <p>
 * 友链记录表 前端控制器
 * </p>
 *
 * @author zhanglei
 * @since 2019-07-31
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/friend/link")
public class FriendLinkController {


  @Autowired
  FriendLinkService friendLinkService;
  @Autowired
  FriendLinkHelper friendLinkHelper;

  /**
   * 分页查询.
   *
   * @author zhanglei
   * @date 2019-08-05  20:29
   */
  @GetMapping("/page")
  public Result<PageInfo<FriendLink>> getByPaginate(@RequestParam(defaultValue = "1") Long pageNumber,
                                                    @RequestParam(defaultValue = "10") Long pageSize,
                                                    FriendLinkRequest friendLinkRequest) {
    log.info("开始进行分页查询友链列表，查询参数为 '{}' ", friendLinkRequest);
    FriendLink friendLink = new FriendLink();
    if (Objects.nonNull(friendLinkRequest)) {
      BeanUtils.copyProperties(friendLinkRequest,friendLink);
    }
    Page<FriendLink> friendLinkPage = (Page<FriendLink>) friendLinkService
        .page(new Page<>(pageNumber, pageSize), new QueryWrapper(friendLink));
    return Result.of(friendLinkHelper.toPageInfo(friendLinkPage));
  }

  /**
   * 新增友链信息.
   *
   * @author zhanglei
   * @date 2019-08-05  20:29
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @PostMapping("")
  public Result<Boolean> saveFriendLink(@RequestBody FriendLinkRequest request) {
    log.info("开始新增一条友链信息,参数为{}", request);
    return Result.of(friendLinkService.saveFriendLink(request));
  }

  /**
   * 修改友链信息.
   *
   * @author zhanglei
   * @date 2019-08-05  20:29
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @PutMapping("")
  public Result<Boolean> updateFriendLink(@RequestBody FriendLinkRequest request) {
    log.info("开始修改增一条友链信息,参数为{}", request);
    return Result.of(friendLinkService.updateFriendLink(request));
  }

  /**
   * 删除友链信息.
   *
   * @author zhanglei
   * @date 2019-08-05  20:29
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @DeleteMapping("/{id}")
  public Result<Boolean> deleteById(@PathVariable(value = "id") Long id) {
    log.info("开始删除一条友链信息,id为{}", id);
    return Result.of(friendLinkService.removeById(id));
  }
}

