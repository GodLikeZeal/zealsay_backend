package com.zeal.zealsay.helper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.constant.SystemConstants;
import com.zeal.zealsay.common.constant.enums.Role;
import com.zeal.zealsay.common.constant.enums.UserStatus;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.dto.request.UserAddRequest;
import com.zeal.zealsay.dto.request.UserRegisterRequest;
import com.zeal.zealsay.dto.response.UserResponse;
import com.zeal.zealsay.entity.Dict;
import com.zeal.zealsay.entity.User;
import com.zeal.zealsay.service.DictService;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
    SystemConstants systemConstants;
    @Autowired
    DictService dictService;

    /**
     * 转换成返回列表.
     *
     * @author zhanglei
     * @date 2018/11/15  9:25 PM
     */
    public PageInfo<UserResponse> toPageInfo(Page<User> userPage) {
        List<UserResponse> userResponses = userPage.getRecords()
                .stream()
                .map(s -> {
                    UserResponse response = new UserResponse();
                    BeanUtils.copyProperties(s, response);
                    return response;
                })
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
        User user = new User();
        BeanUtils.copyProperties(userAddRequest, user);
        return user.toBuilder()
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
        User user = new User();
        BeanUtils.copyProperties(userRegisterRequest, user);
        return user.toBuilder()
                .password(new BCryptPasswordEncoder().encode(userRegisterRequest.getPassword()))
                .status(UserStatus.NORMAL)
                .avatar(gennerateAvatar())
                .emailConfirm(false)
                .role(Role.ROLE_USER)
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
        if (Objects.nonNull(user.getArea())) {
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
        if (Objects.nonNull(user.getProvince())) {
            queryWrapper.like("province", user.getProvince());
        }
        if (Objects.nonNull(user.getCity())) {
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

    public UserResponse toUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        //解析省市区
        if (Objects.nonNull(user.getProvince())) {
            List<Dict> dicts = dictService.list(new QueryWrapper<Dict>().eq("code", user.getProvince()));
            if (!CollectionUtils.isEmpty(dicts)) {
                userResponse.setProvinceName(dicts.get(0).getName());
            }
        }
        //解析省市区
        if (Objects.nonNull(user.getCity())) {
            List<Dict> dicts = dictService.list(new QueryWrapper<Dict>().eq("code", user.getCity()));
            if (!CollectionUtils.isEmpty(dicts)) {
                userResponse.setCityName(dicts.get(0).getName());
            }
        }
        //解析省市区
        if (Objects.nonNull(user.getArea())) {
            List<Dict> dicts = dictService.list(new QueryWrapper<Dict>().eq("code", user.getArea()));
            if (!CollectionUtils.isEmpty(dicts)) {
                userResponse.setAreaName(dicts.get(0).getName());
            }
        }
        return userResponse;
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
