package com.zeal.zealsay.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zeal.zealsay.converter.RoleConvertMapper;
import com.zeal.zealsay.dto.request.RoleAddResquest;
import com.zeal.zealsay.dto.request.RoleUpdateRequest;
import com.zeal.zealsay.entity.Role;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.mapper.RoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
public class RoleService extends ServiceImpl<RoleMapper, Role> implements IService<Role> {

  @Autowired
  RoleConvertMapper roleConvertMapper;

  /**
   * 添加角色.
   *
   * @param roleAddResquest 添加角色入参
   * @return  boolean 是否成功
   * @author  zhanglei
   * @date 2018/11/23  4:55 PM
   */
  public Boolean addRole(RoleAddResquest roleAddResquest) {
    Role role = roleConvertMapper.toRole(roleAddResquest);
    //校验是否重复
      List<Role> roles = list(new QueryWrapper<>(role));
      if (CollectionUtils.isNotEmpty(roles)) {
          throw new ServiceException("系统已存在相同角色信息！");
      }
    //添加之前初始化
    role.setCreateAt(LocalDateTime.now());
    return save(role);
  }

  public Boolean updateRole(RoleUpdateRequest roleUpdateRequest) {
    Role role = roleConvertMapper.toRole(roleUpdateRequest);
      //校验是否重复
      List<Role> roles = list(new QueryWrapper<>(role));
      if (CollectionUtils.isNotEmpty(roles)) {
          throw new ServiceException("系统已存在相同角色信息！");
      }
    //更新之前初始化
    role.setUpdateAt(LocalDateTime.now());
    return updateById(role);
  }
}
