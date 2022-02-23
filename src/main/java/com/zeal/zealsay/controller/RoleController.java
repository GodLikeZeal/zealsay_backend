package com.zeal.zealsay.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.dto.request.RoleAddResquest;
import com.zeal.zealsay.dto.request.RolePageRequest;
import com.zeal.zealsay.dto.request.RoleUpdateRequest;
import com.zeal.zealsay.dto.response.RoleResponse;
import com.zeal.zealsay.entity.Role;
import com.zeal.zealsay.helper.RoleHelper;
import com.zeal.zealsay.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 角色模块接口入口.
 *
 * @author zhanglei
 * @date 2018/11/23  6:01 PM
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    @Autowired
    RoleService roleService;
    @Autowired
    RoleHelper roleHelper;

    /**
     * 根据id来查询.
     *
     * @author zhanglei
     * @date 2018/9/7  下午6:00
     */
    @GetMapping("/{id}")
    public Result<RoleResponse> getById(@PathVariable String id) {
        log.info("开始查询角色id为 '{}' 的角色详情信息", id);
        Role role = roleService.getById(id);
        if (Objects.isNull(role)) {
            return Result.of(null);
        }
        RoleResponse response = new RoleResponse();
        BeanUtils.copyProperties(role, response);
        return Result.of(response);
    }

    /**
     * 根据id来查询.
     *
     * @author zhanglei
     * @date 2018/9/7  下午6:00
     */
    @GetMapping("")
    public Result<RoleResponse> getByList() {
        log.info("开始查询角色列表");
        List<Role> list = roleService.list(new QueryWrapper<Role>());
        if (CollectionUtils.isEmpty(list)) {
            return Result.of(null);
        }
        List<RoleResponse> responses = list.stream().map(s -> {
            RoleResponse response = new RoleResponse();
            BeanUtils.copyProperties(s, response);
            return response;
        }).collect(Collectors.toList());
        return Result.of(responses);
    }

    /**
     * 分页查询.
     *
     * @author zhanglei
     * @date 2018/9/7  下午6:00
     */
    @GetMapping("/page")
    public Result<PageInfo<RoleResponse>> getByPaginate(@Value("1") Long pageNumber,
                                                        @Value("10") Long pageSize,
                                                        RolePageRequest rolePageRequest) {
        log.info("开始进行分页查询角色列表，查询参数为 '{}' ", rolePageRequest);
        Role role = new Role();
        if (Objects.nonNull(rolePageRequest)) {
            BeanUtils.copyProperties(rolePageRequest, role);
        }
        Page<Role> rolePage = (Page<Role>) roleService
                .page(new Page<>(pageNumber, pageSize), new QueryWrapper(role));
        return Result.of(roleHelper.toPageInfo(rolePage));
    }

    /**
     * 新增角色.
     *
     * @author zhanglei
     * @date 2018/11/23  5:43 PM
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("")
    public Result<Boolean> addRole(@RequestBody @Validated RoleAddResquest roleAddResquest) {
        log.info("开始进行新增角色，新增参数为 '{}' ", roleAddResquest);
        return Result.of(roleService.addRole(roleAddResquest));
    }

    /**
     * 修改角色数据.
     *
     * @author zhanglei
     * @date 2018/11/23  5:47 PM
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("")
    public Result<Boolean> updateRole(@RequestBody @Validated RoleUpdateRequest roleUpdateRequest) {
        log.info("开始进行分新增角色，新增参数为 '{}' ", roleUpdateRequest);
        return Result.of(roleService.updateRole(roleUpdateRequest));
    }

    /**
     * 根据id删除角色.
     *
     * @author zhanglei
     * @date 2018/11/23  5:47 PM
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteRole(@PathVariable Long id) {
        log.info("开始删除id为 '{}' 的角色信息", id);
        return Result.of(roleService.removeById(id));
    }

    /**
     * 根据id列表批量删除角色.
     *
     * @author zhanglei
     * @date 2018/11/23  5:47 PM
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/batch")
    public Result<Boolean> deleteRoleBatch(@RequestBody Collection<String> ids) {
        log.info("开始批量删除id在 '{}' 的角色信息", ids.toString());
        return Result.of(roleService.removeByIds(ids));
    }
}

