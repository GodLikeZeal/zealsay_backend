package com.zeal.zealsay.helper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.constant.enums.UserStatus;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.SecuityUser;
import com.zeal.zealsay.converter.UserConvertMapper;
import com.zeal.zealsay.dto.request.UserAddRequest;
import com.zeal.zealsay.dto.request.UserUpdateRequest;
import com.zeal.zealsay.dto.response.UserResponse;
import com.zeal.zealsay.entity.BlockLog;
import com.zeal.zealsay.entity.User;
import com.zeal.zealsay.service.auth.UserDetailServiceImpl;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户帮助类.
 *
 * @author zhanglei
 * @date 2018/11/15  6:53 PM
 */
@Component
public class BlockLogHelper {

  @Autowired
  UserDetailServiceImpl userDetailService;

  /**
   * 更新之前通过请求参数转换成user.
   *
   * @author zhanglei
   * @date 2018/11/15  7:46 PM
   */
  public BlockLog toBlockLog(UserStatus userStatus) {
    SecuityUser secuityUser = userDetailService.getCurrentUser();

    return BlockLog.builder()
            .operatorId(Objects.isNull(secuityUser.getUserId()) ? 0 :secuityUser.getUserId())
            .build();
  }

}
