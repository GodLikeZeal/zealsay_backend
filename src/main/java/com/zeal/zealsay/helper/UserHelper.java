package com.zeal.zealsay.helper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.constant.SystemConstants;
import com.zeal.zealsay.common.constant.enums.Role;
import com.zeal.zealsay.common.constant.enums.UserStatus;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.SecuityUser;
import com.zeal.zealsay.converter.UserConvertMapper;
import com.zeal.zealsay.dto.request.UserAddRequest;
import com.zeal.zealsay.dto.request.UserRegisterRequest;
import com.zeal.zealsay.dto.response.ArticleResponse;
import com.zeal.zealsay.dto.response.UserResponse;
import com.zeal.zealsay.entity.Article;
import com.zeal.zealsay.entity.ArticleCategory;
import com.zeal.zealsay.entity.Dict;
import com.zeal.zealsay.entity.User;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.service.DictService;
import com.zeal.zealsay.service.auth.UserDetailServiceImpl;
import com.zeal.zealsay.util.RedisUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 用户帮助类.
 *
 * @author zhanglei
 * @date 2018/11/15  6:53 PM
 */
@Slf4j
@Component
public class UserHelper {

    @Autowired
    UserDetailServiceImpl userDetailService;
    @Autowired
    UserConvertMapper userConvertMapper;
    @Autowired
    SystemConstants systemConstants;
    @Autowired
    DictService dictService;
    @Autowired
    private RedisUtil redisUtils;

    /**
     * 转换成返回列表.
     *
     * @author zhanglei
     * @date 2018/11/15  9:25 PM
     */
    public PageInfo<UserResponse> toPageInfo(Page<User> userPage) {
        List<UserResponse> userResponses = userPage.getRecords()
                .stream()
                .map(this::apply)
                .collect(Collectors.toList());
        return PageInfo.<UserResponse>builder()
                .records(userResponses)
                .currentPage(userPage.getCurrent())
                .pageSize(userPage.getSize())
                .total(userPage.getTotal())
                .build();
    }

    /**
     * 解析分类目录和作者信息.
     *
     * @author zhanglei
     * @date 2019-07-29  18:22
     */
    private UserResponse apply(User s) {
        UserResponse userResponse = userConvertMapper.toUserResponse(s);
        //加密隐私数据
        SecuityUser user = userDetailService.getCurrentUser();
        if (Objects.isNull(user)) {
            throw new ServiceException("需要登录");
        }
        if (!user.getRole().equals(Role.ROLE_ADMIN)) {
            if (StringUtils.isNotBlank(userResponse.getPhoneNumber())) {
                userResponse.setPhoneNumber(userResponse.getPhoneNumber()
                        .replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
            }
            if (StringUtils.isNotBlank(userResponse.getEmail())) {
                userResponse.setEmail(userResponse.getEmail()
                        .replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4"));
            }
        }
        return userResponse;
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
        String em = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        String ph = "^[1][34578]\\d{9}$";
        User user = userConvertMapper.toUser(userRegisterRequest);
        user.setPassword(new BCryptPasswordEncoder().encode(userRegisterRequest.getPassword()));
        user.setStatus(UserStatus.NORMAL);
        user.setAvatar(gennerateAvatar());
        user.setRole(Role.ROLE_USER);
        user.setIntroduction("这人懒死了，什么都没有写⊙﹏⊙∥∣°");
        user.setRegisterDate(LocalDateTime.now());
        if (userRegisterRequest.getEmailOrPhone().matches(ph)) {
            user.setPhoneNumber(userRegisterRequest.getEmailOrPhone());
        } else if (userRegisterRequest.getEmailOrPhone().matches(em)) {
            user.setEmail(userRegisterRequest.getEmailOrPhone());
            user.setEmailConfirm(true);
        } else {
            throw new ServiceException("信息遭到篡改，以取消");
        }
        return user;
    }

    /**
     * 生成验证码.
     *
     * @author zhanglei
     * @date 2020/6/26 13:01
     */
    public String gennerateValidCode() {
        String code = (int) ((Math.random() * 9 + 1) * 100000) + "";
        log.info("生成验证码为:{}", code);
        return code;
    }

    /**
     * 保存验证码.
     *
     * @author  zhanglei
     * @date 2020/6/26 13:22
     */
    public void saveValidCode(String phone, String code) {
        //先判断是否存在
        Object smsNum = redisUtils.get("SMS_NUM_" + phone);
        int n = 0;
        if (Objects.nonNull(smsNum)) {
            //判断是否超过5次
            n = (int) smsNum;
            if (n >= 5) {
                throw new ServiceException("发送次数超限制,24小时后再试吧");
            }
            redisUtils.set("SMS_NUM_" + phone,++n);
        } else {
            //24小时内只能发5次
            redisUtils.set("SMS_NUM_" + phone,++n,24*60);
        }
        //5分钟过期
        redisUtils.set("SMS_" + phone,code,5*60);
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
        UserResponse userResponse = userConvertMapper.toUserResponse(user);
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
