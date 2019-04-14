package com.zeal.zealsay.helper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.converter.RoleConvertMapper;
import com.zeal.zealsay.dto.request.RoleAddResquest;
import com.zeal.zealsay.dto.request.RoleUpdateRequest;
import com.zeal.zealsay.dto.response.RoleResponse;
import com.zeal.zealsay.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色帮助类.
 *
 * @author  zhanglei
 * @date 2018/11/15  6:53 PM
 */
@Component
public class RoleHelper {

  @Autowired
  RoleConvertMapper roleConvertMapper;

  /**
   * 更新之前通过请求参数转换成Role.
   *
   * @author  zhanglei
   * @date 2018/11/15  7:46 PM
   */
  public Role initBeforeUpdate(RoleUpdateRequest roleUpdateRequest){
    Role role = roleConvertMapper.toRole(roleUpdateRequest);
    return role;
  }

  /**
   * 添加之前通过请求参数转换成Role.
   *
   * @author  zhanglei
   * @date 2018/11/15  7:46 PM
   */
  public Role initBeforeAdd(RoleAddResquest roleAddResquest){
    Role role = roleConvertMapper.toRole(roleAddResquest);
    role.setCreateDate(LocalDateTime.now());
    role.setIsDel(false);
    return role;
  }

  /**
   * 转换成返回列表.
   *
   * @author  zhanglei
   * @date 2018/11/15  9:25 PM
   * @param rolePage
   */
  public PageInfo<RoleResponse> toPageInfo(Page<Role> rolePage){
    PageInfo<Role> rolePageInfo = new PageInfo(rolePage);
    List<RoleResponse> roleResponses = rolePage.getRecords()
        .stream()
        .map(s -> roleConvertMapper.toRoleResponse(s))
        .collect(Collectors.toList());
    return PageInfo.<RoleResponse>builder()
        .records(roleResponses)
        .currentPage(rolePageInfo.getCurrentPage())
        .pageSize(rolePageInfo.getPageSize())
        .total(rolePageInfo.getTotal())
        .build();
  }
}
