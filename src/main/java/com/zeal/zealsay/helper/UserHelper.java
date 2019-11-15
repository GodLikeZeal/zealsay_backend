package com.zeal.zealsay.helper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.constant.SystemConstants;
import com.zeal.zealsay.common.constant.enums.Role;
import com.zeal.zealsay.common.constant.enums.UserStatus;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.converter.UserConvertMapper;
import com.zeal.zealsay.dto.request.UserAddRequest;
import com.zeal.zealsay.dto.request.UserRegisterRequest;
import com.zeal.zealsay.dto.response.UserResponse;
import com.zeal.zealsay.entity.User;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 用户帮助类.
 *
 * @author zhanglei
 * @date 2018/11/15  6:53 PM
 */
@Component
public class UserHelper {

  @Autowired
  UserConvertMapper userConvertMapper;
  @Autowired
  SystemConstants systemConstants;

  /**
   * 转换成返回列表.
   *
   * @author zhanglei
   * @date 2018/11/15  9:25 PM
   */
  public PageInfo<UserResponse> toPageInfo(Page<User> userPage) {
    List<UserResponse> userResponses = userPage.getRecords()
        .stream()
        .map(s -> userConvertMapper.toUserResponse(s))
        .collect(Collectors.toList());
    return PageInfo.<UserResponse>builder()
        .records(userResponses)
        .currentPage(userPage.getCurrent())
        .pageSize(userPage.getSize())
        .total(userPage.getTotal())
        .build();
  }

  /**
   * 添加之前通过请求参数转换成user.
   *
   * @author zhanglei
   * @date 2018/11/15  7:46 PM
   */
  public User initBeforeAdd(UserAddRequest userAddRequest) {
    return userConvertMapper.toUser(userAddRequest).toBuilder()
        .password(new BCryptPasswordEncoder().encode(userAddRequest.getPassword()))
        .status(UserStatus.NORMAL)
        .emailConfirm(true)
        .introduction("这人懒死了，什么都没有写⊙﹏⊙∥∣°")
        .registerDate(LocalDateTime.now())
        .build();
  }

  /**
   * 自主注册，添加之前通过请求参数转换成user.
   *
   * @author zhanglei
   * @date 2018/11/15  7:46 PM
   */
  public User initBeforeAdd(UserRegisterRequest userRegisterRequest) {
    return userConvertMapper.toUser(userRegisterRequest).toBuilder()
        .password(new BCryptPasswordEncoder().encode(userRegisterRequest.getPassword()))
        .status(UserStatus.NORMAL)
        .avatar(gennerateAvatar())
        .emailConfirm(false)
        .role(Role.USER)
        .introduction("这人懒死了，什么都没有写⊙﹏⊙∥∣°")
        .registerDate(LocalDateTime.now())
        .build();
  }

  /**
   * 构造模糊查询参数.
   *
   * @author zhanglei
   * @date 2019-03-08  11:55
   */
  public QueryWrapper<User> buildVagueQuery(@NonNull User user) {
    QueryWrapper<User> queryWrapper = new QueryWrapper();
    if (StringUtils.isNotBlank(user.getAddress())) {
      queryWrapper.like("address", user.getAddress());
    }
    if (StringUtils.isNotBlank(user.getArea())) {
      queryWrapper.like("area", user.getArea());
    }
    if (StringUtils.isNotBlank(user.getUsername())) {
      queryWrapper.like("username", user.getUsername());
    }
    if (StringUtils.isNotBlank(user.getName())) {
      queryWrapper.like("name", user.getName());
    }
    if (StringUtils.isNotBlank(user.getLabel())) {
      queryWrapper.like("label", user.getLabel());
    }
    if (StringUtils.isNotBlank(user.getPhoneNumber())) {
      queryWrapper.like("phone_number", user.getPhoneNumber());
    }
    if (StringUtils.isNotBlank(user.getEmail())) {
      queryWrapper.like("email", user.getEmail());
    }
    if (StringUtils.isNotBlank(user.getAddress())) {
      queryWrapper.like("address", user.getAddress());
    }
    if (StringUtils.isNotBlank(user.getProvince())) {
      queryWrapper.like("province", user.getProvince());
    }
    if (StringUtils.isNotBlank(user.getCity())) {
      queryWrapper.like("city", user.getCity());
    }
    if (StringUtils.isNotBlank(user.getAddress())) {
      queryWrapper.like("address", user.getAddress());
    }
    if (!Objects.isNull(user.getAge())) {
      queryWrapper.like("age", user.getAge());
    }
    if (!Objects.isNull(user.getSex())) {
      queryWrapper.like("sex", user.getSex());
    }
    return queryWrapper;
  }

  /**
   * 生成头像.
   *
   * @author zhanglei
   * @date 2019-10-24  16:03
   */
  public String gennerateAvatar() {
    StringBuffer sb = new StringBuffer();
    Random random = new Random();
    Integer i = random.nextInt(10) * 12 + 1;
    sb.append(systemConstants.getQiniuDomain());
    sb.append("avatar/");
    sb.append(i);
    sb.append(".jpg");
    return sb.toString();
  }
}
