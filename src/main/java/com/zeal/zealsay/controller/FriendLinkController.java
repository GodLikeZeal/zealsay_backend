package com.zeal.zealsay.controller;


import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.dto.request.FriendLinkRequest;
import com.zeal.zealsay.entity.FriendLink;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 友链记录表 前端控制器
 * </p>
 *
 * @author zhanglei
 * @since 2019-07-31
 */
@Api(tags = "友链模块")
@RestController
@RequestMapping("/friend/link")
public class FriendLinkController {


  /**
   * 分页查询.
   *
   * @author  zhanglei
   * @date 2019-08-05  20:29
   */
  @GetMapping("/page")
  @ApiOperation(value = "分页查询友链信息列表",notes = "分页查询友链信息列表")
  public Result<PageInfo<FriendLink>> getByPaginate() {
    return Result.ok();
  }

  /**
   * 新增友链信息.
   *
   * @author  zhanglei
   * @date 2019-08-05  20:29
   */
  @PostMapping("")
  @ApiOperation(value = "新增友链信息",notes = "新增一条友链信息")
  public Result<Boolean> saveFriendLink(@RequestBody FriendLinkRequest request) {
    return Result.ok();
  }

  /**
   * 修改友链信息.
   *
   * @author  zhanglei
   * @date 2019-08-05  20:29
   */
  @PutMapping("")
  @ApiOperation(value = "修改友链信息",notes = "修改一条友链信息")
  public Result<Boolean> updateFriendLink() {
    return Result.ok();
  }

  /**
   * 删除友链信息.
   *
   * @author  zhanglei
   * @date 2019-08-05  20:29
   */
  @DeleteMapping("/{id}")
  @ApiOperation(value = "删除友链信息",notes = "删除一条友链信息")
  public Result<Boolean> deleteById(@PathVariable(value = "id") Long id) {
    return Result.ok();
  }
}

