package com.zeal.zealsay.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zeal.zealsay.common.constant.enums.UserStatus;
import com.zeal.zealsay.entity.User;
import com.zeal.zealsay.common.entity.UserVo;
import com.zeal.zealsay.mapper.RoleMapper;
import com.zeal.zealsay.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhanglei
 * @since 2018-09-14
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IService<User>{

  @Autowired
  RoleMapper roleMapper;

  /**
  * 通过手机号，用户名或者邮箱查询.
  *
  * @author  zeal
  * @date 2018/11/24 14:03
  */
  public UserVo userFind(String  s){
    //判断是邮箱还是手机号的正则表达式
    String em = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    String ph = "^[1][34578]\\d{9}$";
    User user = null;
    QueryWrapper<User> qu = new QueryWrapper<>();
    if (s.matches(ph)){
      //手机号登录
      user = baseMapper.selectOne(qu.eq("phone_number",s));
    }else  if (s.matches(em)){
      //邮箱登录
      user = baseMapper.selectOne(qu.eq("email",s));
    }else {
      //用户名
      user = baseMapper.selectOne(qu.eq("username",s));
    }
    if (user!=null){
      return  UserVo.builder()
          .user(user)
          .role(roleMapper.selectById(user.getRoleId()))
          .build();
    }
    return UserVo.builder()
        .user(user)
        .build();
  }

  /**
  * 禁用用户.
  *
  * @author  zeal
  * @date 2018/11/24 14:27
  */
public Boolean markUserDisabled(Long userId) {
      return updateById(User.builder()
          .id(userId)
          .status(UserStatus.DISABLED)
          .build());
}
}
