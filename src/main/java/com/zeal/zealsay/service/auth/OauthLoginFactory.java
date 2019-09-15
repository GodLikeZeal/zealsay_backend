package com.zeal.zealsay.service.auth;

import com.zeal.zealsay.common.constant.enums.OauthSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
* 第三方登录简单工厂.
*
* @author  zeal
* @date 2019/9/11 21:01
*/
@Service
public class OauthLoginFactory {

    @Autowired
    Map<String, OauthLogin> oauthLogins = new ConcurrentHashMap<>(2);

    public OauthLogin getOauthLogin(OauthSource oauthSource) {
        OauthLogin oauthLogin = oauthLogins.get(oauthSource.name());
        if(oauthLogin == null) {
            throw new RuntimeException("no oauthLogin defined");
        }
        return oauthLogin;
    }
}
