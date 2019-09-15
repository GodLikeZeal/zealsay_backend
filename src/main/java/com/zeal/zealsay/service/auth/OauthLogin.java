package com.zeal.zealsay.service.auth;

import me.zhyd.oauth.model.AuthResponse;

import java.util.Map;

/**
* 登录相关逻辑.
*
* @author  zeal
* @date 2019/9/11 21:06
*/
public interface OauthLogin {

    /**
     * @param authResponse 第三方登录实体
     * 登录.
     * @return
     */
    Map<String,Object> login(AuthResponse authResponse);
}
