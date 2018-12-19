package com.zeal.zealsay.helper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.converter.UserConvertMapper;
import com.zeal.zealsay.dto.request.UserUpdateRequest;
import com.zeal.zealsay.dto.response.UserResponse;
import com.zeal.zealsay.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户帮助类.
 *
 * @author  zhanglei
 * @date 2018/11/15  6:53 PM
 */
@Component
public class UserHelper {

  @Autowired
  UserConvertMapper userConvertMapper;
  /**
   * 更新之前通过请求参数转换成user.
   *
   * @author  zhanglei
   * @date 2018/11/15  7:46 PM
   */
  public User initBeforeUpdate(UserUpdateRequest userUpdateRequest){
    return userConvertMapper.toUser(userUpdateRequest);
  }

  /**
   * 转换成返回列表.
   *
   * @author  zhanglei
   * @date 2018/11/15  9:25 PM
   */
  public PageInfo<UserResponse> toPageInfo(Page<User> userPage){
    PageInfo<User> userPageInfo = new PageInfo(userPage);
    List<UserResponse> userResponses = userPageInfo.getRecords()
        .stream()
        .map(s -> userConvertMapper.toUserResponse(s))
        .collect(Collectors.toList());
    return PageInfo.<UserResponse>builder()
        .records(userResponses)
        .currentPage(userPageInfo.getCurrentPage())
        .pageSize(userPageInfo.getPageSize())
        .total(userPageInfo.getTotal())
        .build();
  }
}
