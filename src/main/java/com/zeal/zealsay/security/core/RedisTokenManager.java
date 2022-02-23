package com.zeal.zealsay.security.core;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeal.zealsay.common.entity.SecuityUser;
import com.zeal.zealsay.common.entity.UserInfo;
import com.zeal.zealsay.util.RSAUtil;
import com.zeal.zealsay.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

/**
* Redis token实现.
*
* @author  zeal
* @date 2020/1/12 21:57
*/
@Component
public class RedisTokenManager implements TokenManager{

    @Autowired
    private RedisUtil redisUtils;
    @Autowired
    ObjectMapper objectMapper;


    /**
     * 创建token
     * @param secuityUser
     * @return
     */
    @Override
    public String generateToken(SecuityUser secuityUser) throws Exception {
        return generateToken(secuityUser.getUserId());
    }

    @Override
    public String generateToken(Long id) throws Exception {
        StringBuilder tokenBuilder = new StringBuilder();
        //生成未加密的token:
        tokenBuilder.append("token:")
                .append(id).append(":")
                .append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+":")
                .append(new Random().nextInt((999999 - 111111 + 1)) + 111111);
        return (RSAUtil.privateEncrypt(tokenBuilder.toString(), RSAUtil.getPrivateKey(RSAUtil.privateKey)));
    }

    @Override
    public String saveToken(UserInfo user) throws Exception {
        String token = generateToken(user.getId());
        // 设置zset用于线程根据分数定时清理不活跃用户
        redisUtils.zsetAdd(MEMBER_TOKEN, token, Double.valueOf(System.currentTimeMillis()));
        // 存储相关用户信息(权限等信息)
        redisUtils.set(token, user, 60 * 60 * 24 * 30L);
        // 用于重新登录，但之前token还存在的情况,通过id获取相应的token来进行之前的token清理
        redisUtils.set(String.valueOf(user.getId()), token);
        return token;
    }

    @Override
    public void delToken(UserInfo user) {
        Object object = redisUtils.get(String.valueOf(user.getId()));
        if (object != null) {
            String token = (String) object;
            // 移除token以及相对应的权限信息
            redisUtils.del(token);
            // 移除token
            redisUtils.del(String.valueOf(user.getId()));
            // 移除zset排行的token，避免一个用户重复排行
            redisUtils.remove(MEMBER_TOKEN, token);
        }
    }


    @Override
    public UserInfo getUserInfoByToken(String token) throws JsonProcessingException {
        // 获取redis已有的member信息，不查数据库,重新生成token放入
        UserInfo user =  objectMapper.readValue(JSON.toJSONString(redisUtils.get(token)),UserInfo.class);
        return Objects.nonNull(user)? user : null;
    }
}
