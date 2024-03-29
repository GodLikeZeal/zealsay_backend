package com.zeal.zealsay.security.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zeal.zealsay.common.entity.SecuityUser;
import com.zeal.zealsay.common.entity.UserInfo;

public interface TokenManager {

    String MEMBER_TOKEN = "MEMBER_TOKEN";

    /**
     * 创建token.
     * @param secuityUser
     * @return
     */
    String generateToken(SecuityUser secuityUser) throws Exception;

    /**
     * 生成token前的格式为token:id:时间:六位随机数.
     * @param id
     * @return
     */
    String generateToken(Long id) throws Exception;

    /**
     * 保存token.
     * @param user
     * @return
     */
    String saveToken(UserInfo user) throws Exception;

    /**
     * 删除token.
     * @param user
     */
    void delToken(UserInfo user);

    /**
     * 获取用户信息.
     * @param token
     * @return
     */
    UserInfo getUserInfoByToken(String token) throws JsonProcessingException;
}
