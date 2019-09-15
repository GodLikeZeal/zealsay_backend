package com.zeal.zealsay.service.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zeal.zealsay.common.constant.enums.OauthSource;
import com.zeal.zealsay.common.entity.SecuityUser;
import com.zeal.zealsay.entity.AuthUser;
import com.zeal.zealsay.service.AuthUserService;
import com.zeal.zealsay.service.UserService;
import com.zeal.zealsay.util.JwtTokenUtil;
import lombok.NonNull;
import me.zhyd.oauth.model.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 登录业务抽象类.
*
* @author  zeal
* @date 2019/9/11 21:04
*/
public abstract class AbstractOauthLogin implements OauthLogin {

    @Autowired
    AuthUserService authUserService;
    @Autowired
    UserDetailServiceImpl userDetailsService;
    @Autowired
    UserService userService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public Map<String,Object> login(AuthResponse authResponse) {
        Map<String,Object> map = new HashMap<>();
        // 执行登录逻辑
        me.zhyd.oauth.model.AuthUser authUser = (me.zhyd.oauth.model.AuthUser) authResponse.getData();
        //判断是否注册
        //首先保存授权用户记录
        AuthUser user = toAuthUser(authUser);
        if (isRegister(authUser.getUuid(),getSource())) {
            //生成token并且登录
            SecuityUser secuityUser = userDetailsService
                .toSecuityUser(userService.getByAuthUser(authUser.getUuid(),getSource()));
            String token = jwtTokenUtil.generateToken(secuityUser);
            map.put("token",token);
            map.put("redirect","");
            map.put("register",true);
        } else {
            authUserService.save(user);
            map.put("key",user.getId());
            map.put("register",false);
        }
        map.put("bind",user.getBind());
        return map;
    }

    protected abstract String getRedirectUrl();

    protected abstract OauthSource getSource();

    /**
     *是否注册过.
     * @return
     */
    private boolean isRegister(@NonNull String uid, OauthSource source) {
        List<AuthUser> authUsers = authUserService
            .list(new QueryWrapper<AuthUser>()
                .eq("uid", uid)
                .eq("source",source));
        return !CollectionUtils.isEmpty(authUsers);
    }

    /**
     * 构建Auth实例.
     * @param authUser
     * @return
     */
    private AuthUser toAuthUser(me.zhyd.oauth.model.AuthUser authUser) {
        return AuthUser.builder()
            .uid(authUser.getUuid())
            .username(authUser.getUsername())
            .nickname(authUser.getNickname())
            .avatar(authUser.getAvatar())
            .blog(authUser.getBlog())
            .company(authUser.getCompany())
            .location(authUser.getLocation())
            .email(authUser.getEmail())
            .remark(authUser.getRemark())
            .gender(authUser.getGender().name())
            .bind(false)
            .source(authUser.getSource().name())
            .build();
    }
}
