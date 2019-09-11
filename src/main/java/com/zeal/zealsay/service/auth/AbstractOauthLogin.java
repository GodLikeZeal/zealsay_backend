package com.zeal.zealsay.service.auth;

import me.zhyd.oauth.model.AuthResponse;

import javax.servlet.http.HttpServletResponse;

/**
* 登录业务抽象类.
*
* @author  zeal
* @date 2019/9/11 21:04
*/
public abstract class AbstractOauthLogin implements OauthLogin {

    @Override
    public HttpServletResponse login(AuthResponse authResponse) {
        //todo 执行登录逻辑
        return null;
    }

    protected abstract String getRedirectUrl();
}
