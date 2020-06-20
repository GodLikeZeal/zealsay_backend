package com.zeal.zealsay.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zeal.zealsay.converter.RoleConvertMapper;
import com.zeal.zealsay.dto.request.RoleAddResquest;
import com.zeal.zealsay.dto.request.RoleUpdateRequest;
import com.zeal.zealsay.entity.Role;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.helper.RoleHelper;
import com.zeal.zealsay.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhanglei
 * @since 2018-09-14
 */
@Transactional(rollbackFor = {ServiceException.class,RuntimeException.class,Exception.class})
@Service
public class RoleService extends AbstractService<RoleMapper, Role> implements IService<Role> {

  @Autowired
  RoleConvertMapper roleConvertMapper;
  @Autowired
  RoleHelper roleHelper;

  /**
   * 添加角色.
   *
   * @param roleAddResquest 添加角色入参
   * @return  boolean 是否成功
   * @author  zhanglei
   * @date 2018/11/23  4:55 PM
   */
  public Boolean addRole(RoleAddResquest roleAddResquest) {
    Role role = roleHelper.initBeforeAdd(roleAddResquest);
    //校验是否重复
    checkRoleRepeat(role);
    return save(role);
  }

  public Boolean updateRole(RoleUpdateRequest roleUpdateRequest) {
    Role role = roleHelper.initBeforeUpdate(roleUpdateRequest);
      //校验是否重复
    checkRoleRepeat(role);
    return updateById(role);
  }

  /**
  * 校验是否有重复的角色信息.
  *
  * @author  zeal
  * @date 2019/4/14 11:28
  */
  private void checkRoleRepeat(Role role) {
    QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>()
            .and(wrapper -> wrapper.eq("name", role.getName())
                    .or()
                    .eq("value", role.getValue()));
    if (Objects.nonNull(role.getId())) {
      queryWrapper.ne("id",role.getId());
    }
    List<Role> roles = list(queryWrapper);
    if (!CollectionUtils.isEmpty(roles)) {
      throw new ServiceException("系统已存在相同角色信息！");
    }
  }
}
