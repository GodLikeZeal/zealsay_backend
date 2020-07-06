package com.zeal.zealsay.service.auth;

import com.zeal.zealsay.common.constant.SystemConstants;
import com.zeal.zealsay.common.constant.enums.OauthSource;
import com.zeal.zealsay.entity.Dict;
import com.zeal.zealsay.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
* github登录.
*
* @author  zeal
* @date 2019/9/11 21:51
*/
@Service(value = "GITHUB")
public class OauthGithubLogin extends AbstractOauthLogin{

    @Autowired
    DictService dictService;

    @Override
    protected String getRedirectUrl() {
        List<Dict> dicts = null;
        try {
            dicts = dictService.getConfig().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Map<Integer, String> config = dicts.stream().collect(Collectors.toMap(Dict::getCode, Dict::getName));
        return config.get(1000003)+"/redirect";
    }

    @Override
    protected OauthSource getSource() {
        return OauthSource.GITHUB;
    }
}
