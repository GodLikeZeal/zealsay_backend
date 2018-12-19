package com.zeal.zealsay.converter;

import com.zeal.zealsay.dto.request.RoleAddResquest;
import com.zeal.zealsay.dto.request.RolePageRequest;
import com.zeal.zealsay.dto.request.RoleUpdateRequest;
import com.zeal.zealsay.dto.response.RoleResponse;
import com.zeal.zealsay.entity.Role;
import org.mapstruct.Mapper;

/**
 * role相关转换器.
 *
 * @author  zhanglei
 * @date 2018/11/15  5:43 PM
 */
@Mapper(componentModel = "spring")
public interface RoleConvertMapper {

  RoleResponse toRoleResponse(Role role);

  Role toRole(RoleUpdateRequest roleUpdateRequest);

  Role toRole(RoleAddResquest roleAddResquest);

  Role toRole(RolePageRequest rolePageRequest);

}
