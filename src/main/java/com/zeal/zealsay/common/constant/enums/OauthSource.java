package com.zeal.zealsay.common.constant.enums;

import com.zeal.zealsay.service.auth.OauthGithubLogin;
import com.zeal.zealsay.service.auth.OauthLogin;

/**
* 第三方登录.
*
* @author  zeal
* @date 2019/9/11 22:15
*/
public enum OauthSource {

    /**
     * github登录
     */
    GITHUB(OauthGithubLogin.class);

    private Class<? extends OauthLogin> oauthLoginClazz;

    OauthSource(Class<? extends OauthLogin> oauthLoginClazz) {
        this.oauthLoginClazz = oauthLoginClazz;
    }
}
