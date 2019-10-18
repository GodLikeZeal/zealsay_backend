package com.zeal.zealsay.service.auth;

import com.zeal.zealsay.common.constant.SystemConstants;
import com.zeal.zealsay.common.constant.enums.OauthSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* github登录.
*
* @author  zeal
* @date 2019/9/11 21:51
*/
@Service(value = "GITHUB")
public class OauthGithubLogin extends AbstractOauthLogin{

    @Autowired
    SystemConstants systemConstants;

    @Override
    protected String getRedirectUrl() {
        return systemConstants.getDomain()+"/admin/redirect";
    }

    @Override
    protected OauthSource getSource() {
        return OauthSource.GITHUB;
    }
}
