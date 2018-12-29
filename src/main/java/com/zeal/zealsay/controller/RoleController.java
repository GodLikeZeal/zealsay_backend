package com.zeal.zealsay.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.converter.RoleConvertMapper;
import com.zeal.zealsay.dto.request.RoleAddResquest;
import com.zeal.zealsay.dto.request.RolePageRequest;
import com.zeal.zealsay.dto.request.RoleUpdateRequest;
import com.zeal.zealsay.dto.response.RoleResponse;
import com.zeal.zealsay.entity.Role;
import com.zeal.zealsay.helper.RoleHelper;
import com.zeal.zealsay.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * 角色模块接口入口.
 *
 * @author  zhanglei
 * @date 2018/11/23  6:01 PM
 */
@Api(tags = "角色模块")
@Slf4j
@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

  @Autowired
  RoleService roleService;
  @Autowired
  RoleHelper roleHelper;
  @Autowired
  RoleConvertMapper roleConvertMapper;

  /**
   * 根据id来查询.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @GetMapping("/{id}")
  @ApiOperation(value = "根据id查询角色信息",notes = "根据id查询角色信息")
  public Result<RoleResponse> getById(@PathVariable String id) {
    log.info("开始查询角色id为 '{}' 的角色详情信息", id);
    return Result
        .of(roleConvertMapper.toRoleResponse(roleService.getById(id)));
  }

  /**
   * 分页查询.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @GetMapping("/page")
  @ApiOperation(value = "分页查询角色信息列表",notes = "分页查询角色信息列表")
  public Result<PageInfo<RoleResponse>> getByPaginate(@Value("1") Long pageNumber,
                                                                      @Value("10") Long pageSize,
                                                                      RolePageRequest rolePageRequest) {
    log.info("开始进行分页查询角色列表，查询参数为 '{}' ", rolePageRequest);
    Page<Role> rolePage = (Page<Role>) roleService
        .page(new Page<>(pageNumber, pageSize), new QueryWrapper(roleConvertMapper
            .toRole(rolePageRequest)));
    return Result.of(roleHelper.toPageInfo(rolePage));
  }

  /**
   * 新增角色.
   *
   * @author  zhanglei
   * @date 2018/11/23  5:43 PM
   */
  @PostMapping("")
  @ApiOperation(value = "新增角色信息",notes = "新增角色信息")
  public Result<Boolean> addRole(@Validated RoleAddResquest roleAddResquest) {
    log.info("开始进行新增角色，新增参数为 '{}' ", roleAddResquest);
    return Result.of(roleService.addRole(roleAddResquest));
  }

  /**
   * 修改角色数据.
   *
   * @author  zhanglei
   * @date 2018/11/23  5:47 PM
   */
  @PutMapping("")
  @ApiOperation(value = "修改角色信息",notes = "修改角色信息")
  public Result<Boolean> updateRole(@Validated RoleUpdateRequest roleUpdateRequest) {
    log.info("开始进行分新增角色，新增参数为 '{}' ", roleUpdateRequest);
    return Result.of(roleService.updateRole(roleUpdateRequest));
  }

  /**
   * 根据id删除角色.
   *
   * @author  zhanglei
   * @date 2018/11/23  5:47 PM
   */
  @DeleteMapping("/{id}")
  @ApiOperation(value = "根据id删除角色信息",notes = "根据id删除角色信息")
  public Result<Boolean> deleteRole(@PathVariable String id) {
    log.info("开始删除id为 '{}' 的角色信息", id);
    return Result.of(roleService.removeById(id));
  }

  /**
   * 根据id列表批量删除角色.
   *
   * @author  zhanglei
   * @date 2018/11/23  5:47 PM
   */
  @DeleteMapping("/batch")
  @ApiOperation(value = "根据id列表批量删除角色信息",notes = "根据id列表批量删除角色信息")
  public Result<Boolean> deleteRoleBatch(Collection<String> ids) {
    log.info("开始批量删除id在 '{}' 的角色信息", ids.toString());
    return Result.of(roleService.removeByIds(ids));
  }
}

