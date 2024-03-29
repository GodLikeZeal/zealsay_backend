package com.zeal.zealsay.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zeal.zealsay.common.constant.enums.*;
import com.zeal.zealsay.common.entity.UserVo;
import com.zeal.zealsay.dto.request.UserAddRequest;
import com.zeal.zealsay.dto.request.UserRegisterRequest;
import com.zeal.zealsay.dto.request.UserUpdateRequest;
import com.zeal.zealsay.entity.AuthUser;
import com.zeal.zealsay.entity.Role;
import com.zeal.zealsay.entity.User;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.helper.UserHelper;
import com.zeal.zealsay.mapper.RoleMapper;
import com.zeal.zealsay.mapper.UserMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhanglei
 * @since 2018-09-14
 */
@Slf4j
@Transactional(rollbackFor = {ServiceException.class, RuntimeException.class, Exception.class})
@Service
public class UserService extends AbstractService<UserMapper, User> implements IService<User> {

  @Autowired
  RoleMapper roleMapper;
  @Autowired
  UserHelper userHelper;
  @Autowired
  BlockLogService blockLogService;
  @Autowired
  AuthUserService authUserService;
  @Autowired
  EmailService emailService;

  /**
   * 通过手机号，用户名或者邮箱查询.
   *
   * @author zeal
   * @date 2018/11/24 14:03
   */
  public UserVo userFind(String s) {
    //判断是邮箱还是手机号的正则表达式
    String em = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    String ph = "^[1][34578]\\d{9}$";
    User user;
    QueryWrapper<User> qu = new QueryWrapper<>();
    if (s.matches(ph)) {
      //手机号登录
      user = baseMapper.selectOne(qu.eq("phone_number", s));
    } else if (s.matches(em)) {
      //邮箱登录
      user = baseMapper.selectOne(qu.eq("email", s).eq("email_confirm", true));
    } else {
      //用户名
      user = baseMapper.selectOne(qu.eq("username", s));
    }
    if (user != null) {
      return UserVo.builder()
          .user(user)
          .role(roleMapper.selectOne(new QueryWrapper<Role>().eq("value", user.getRole())))
          .build();
    }
    return UserVo.builder()
        .user(user)
        .build();
  }

  /**
   * 禁用用户.
   *
   * @author zeal
   * @date 2018/11/24 14:27
   */
  public Boolean markUserDisabled(Long userId) {
    //记录
    User user = getById(userId);
    blockLogService.saveBlocak(user, BlockType.USER, BlockAction.BAN, "违禁");

    return updateById(User.builder()
        .id(userId)
        .status(UserStatus.DISABLED)
        .build());
  }

  /**
   * 解封用户.
   *
   * @author zeal
   * @date 2018/11/24 14:27
   */
  public Boolean markUnsealing(Long userId) {
    //记录
    User user = getById(userId);
    blockLogService.saveBlocak(user, BlockType.USER, BlockAction.UNSEALING, "");

    return updateById(User.builder()
        .id(userId)
        .status(UserStatus.NORMAL)
        .build());
  }

  /**
   * 批量禁用用户.
   *
   * @author zeal
   * @date 2018/11/24 14:27
   */
  public Boolean markUserDisabledBatch(@NonNull Collection<Long> userIds) {
    //记录
    List<User> users = (List<User>) listByIds(userIds);
    blockLogService.saveBlocakUserBatch(users, BlockType.USER, BlockAction.BAN, "违禁");

    update(User.builder().status(UserStatus.DISABLED).build(), new UpdateWrapper<User>()
        .in("id", userIds));
    return true;
  }

  /**
   * 批量解封用户.
   *
   * @author zeal
   * @date 2018/11/24 14:27
   */
  public Boolean markUnsealingBatch(@NonNull Collection<Long> userIds) {
    //记录
    List<User> users = (List<User>) listByIds(userIds);
    blockLogService.saveBlocakUserBatch(users, BlockType.USER, BlockAction.UNSEALING, "");

    update(User.builder().status(UserStatus.NORMAL).build(), new UpdateWrapper<User>()
        .in("id", userIds));
    return true;
  }

  /**
   * 管理员添加用户.
   *
   * @author zeal
   * @date 2018/11/24 14:27
   */
  public Boolean addUser(UserAddRequest userAddRequest) {
    Integer countName = baseMapper.selectCount(new QueryWrapper<User>()
        .eq("username", userAddRequest.getUsername()));
    if (countName > 0) {
      throw new ServiceException("该用户名已经被使用，无法添加");
    }
    Integer countPhone = baseMapper.selectCount(new QueryWrapper<User>()
        .eq("phone_number", userAddRequest.getPhoneNumber()));
    if (countPhone > 0) {
      throw new ServiceException("该手机号已经注册，无法添加");
    }
    Integer countEmail = baseMapper.selectCount(new QueryWrapper<User>()
        .eq("email", userAddRequest.getPhoneNumber()));
    if (countEmail > 0) {
      throw new ServiceException("该邮箱已经注册，无法添加");
    }
    User user = userHelper.initBeforeAdd(userAddRequest);
    return save(user);
  }

  /**
   * 更新用户信息.
   *
   * @author zeal
   * @date 2018/11/24 14:27
   */
  public Boolean updateUser(UserUpdateRequest userUpdateRequest) {
    //检查是否可以被更新
    checkBeforeUpdate(userUpdateRequest);
    User user = new User();
    BeanUtils.copyProperties(userUpdateRequest,user);
    return updateById(user);
  }

  private void checkBeforeUpdate(UserUpdateRequest userUpdateRequest) {
    //更新前用户名去重校验
    if (getIsInUseByUsername(userUpdateRequest.getUsername(), userUpdateRequest.getId())) {
      throw new ServiceException("用户名已被使用");
    }
    //更新前手机号去重校验
    if (getIsInUseByPhone(userUpdateRequest.getPhoneNumber(), userUpdateRequest.getId())) {
      throw new ServiceException("手机号已被使用");
    }
    //更新前邮箱去重校验
    if (getIsInUseByPhone(userUpdateRequest.getEmail(), userUpdateRequest.getId())) {
      throw new ServiceException("邮箱已被使用");
    }
  }

  /**
   * 查询手机号是否已被使用.
   *
   * @author zeal
   * @date 2018/11/24 14:27
   */
  public Boolean getIsInUseByPhone(String phone) {
    return count(new QueryWrapper<User>().eq("phone_number", phone)) > 0;
  }

  /**
   * 查询手机号是否已被使用(不包含自己本身).
   *
   * @author zeal
   * @date 2018/11/24 14:27
   */
  public Boolean getIsInUseByPhone(String phone, Long userId) {
    if (Objects.isNull(userId)) {
      return getIsInUseByPhone(phone);
    } else {
      return count(new QueryWrapper<User>()
          .eq("phone_number", phone)
          .ne("id", userId)) > 0;
    }
  }

  /**
   * 查询用户名是否已被使用.
   *
   * @author zeal
   * @date 2018/11/24 14:27
   */
  public Boolean getIsInUseByUsername(String username) {
    return count(new QueryWrapper<User>().eq("username", username)) > 0;
  }

  /**
   * 查询用户名是否已被使用(不包含自己本身).
   *
   * @author zeal
   * @date 2018/11/24 14:27
   */
  public Boolean getIsInUseByUsername(String username, Long userId) {
    if (Objects.isNull(userId)) {
      return getIsInUseByUsername(username);
    } else {
      return count(new QueryWrapper<User>()
          .eq("username", username)
          .ne("id", userId)) > 0;
    }
  }

  /**
   * 查询用户名是否已被使用.
   *
   * @author zeal
   * @date 2018/11/24 14:27
   */
  public Boolean getIsInUseByEmail(String email) {
    return count(new QueryWrapper<User>()
        .eq("email", email)
        .eq("email_confirm", true)) > 0;
  }

  /**
   * 查询用户名是否已被使用(不包含自己本身).
   *
   * @author zeal
   * @date 2018/11/24 14:27
   */
  public Boolean getIsInUseByEmail(String email, Long userId) {
    if (Objects.isNull(userId)) {
      return getIsInUseByEmail(email);
    } else {
      return count(new QueryWrapper<User>()
          .eq("email", email)
          .ne("id", userId)) > 0;
    }
  }

  /**
   * 通过第三方用户获取Id.
   *
   * @author zhanglei
   * @date 2019-09-12  15:00
   */
  public User getByAuthUser(String uid, OauthSource source) {
    List<AuthUser> authUsers = authUserService
        .list(new QueryWrapper<AuthUser>()
            .eq("uid", uid)
            .eq("source", source));
    if (CollectionUtils.isEmpty(authUsers)) {
      return null;
    } else {
      AuthUser authUser = authUsers.get(0);
      return getById(authUser.getUserId());
    }
  }

  /**
   * 用户自主注册.
   *
   * @author  zhanglei
   * @date 2019-10-17  17:35
   */
  public Boolean userRegister(UserRegisterRequest userRegisterRequest) {
    //首先校验是否重复
    log.info("校验是否重复");
    checkDuplicate(userRegisterRequest.getUsername(),userRegisterRequest.getPhoneNumber(),userRegisterRequest.getEmail());
    //初始化参数
    User user = userHelper.initBeforeAdd(userRegisterRequest);
    //发送邮件
    if (Objects.nonNull(userRegisterRequest.getEmail())) {
      log.info("开始发送注册邮件");
      //todo 执行发送注册邮件逻辑
    }
    log.info("开始保存用户信息");
    //保存
    return save(user);
  }

  /**
   * 今日新增用户.
   *
   * @author  zhanglei
   * @date 2020/6/12  2:19 下午
   */
  public long countUserAdd() {
    return count(new QueryWrapper<User>().ge("register_date", LocalDateTime.now())
        .lt("register_date",LocalDateTime.now().plusDays(1)));
  }

  /**
   * 总用户.
   *
   * @author  zhanglei
   * @date 2020/6/12  2:19 下午
   */
  public long countUser() {
    return count();
  }

  /**
   * 校验是否重复.
   *
   * @author  zhanglei
   * @date 2019-10-17  17:23
   */
  private void checkDuplicate(String username,String phone,String email) {
    Integer countName = baseMapper.selectCount(new QueryWrapper<User>()
        .eq("username", username));
    if (countName > 0) {
      throw new ServiceException("该用户名已经被使用，无法添加");
    }
    if (StringUtils.isNotBlank(phone)) {
      Integer countPhone = baseMapper.selectCount(new QueryWrapper<User>()
          .eq("phone_number", phone));
      if (countPhone > 0) {
        throw new ServiceException("该手机号已经注册，无法添加");
      }
    }
    if (StringUtils.isNotBlank(email)) {
      Integer countEmail = baseMapper.selectCount(new QueryWrapper<User>()
          .eq("email", email));
      if (countEmail > 0) {
        throw new ServiceException("该邮箱已经注册，无法添加");
      }
    }
  }
}
