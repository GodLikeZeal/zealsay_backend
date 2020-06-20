package com.zeal.zealsay.service;

import com.zeal.zealsay.entity.AuthUser;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.mapper.AuthUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 第三方登录用户信息 服务实现类
 * </p>
 *
 * @author zhanglei
 * @since 2019-09-12
 */
@Transactional(rollbackFor = {ServiceException.class, RuntimeException.class, Exception.class})
@Service
public class AuthUserService extends AbstractService<AuthUserMapper, AuthUser> {

}
