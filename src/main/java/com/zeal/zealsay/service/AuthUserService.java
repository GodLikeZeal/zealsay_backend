package com.zeal.zealsay.service;

import com.zeal.zealsay.entity.AuthUser;
import com.zeal.zealsay.mapper.AuthUserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 第三方登录用户信息 服务实现类
 * </p>
 *
 * @author zhanglei
 * @since 2019-09-12
 */
@Service
public class AuthUserService extends ServiceImpl<AuthUserMapper, AuthUser> {

}
