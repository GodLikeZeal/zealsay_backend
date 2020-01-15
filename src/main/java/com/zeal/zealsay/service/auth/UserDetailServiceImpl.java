package com.zeal.zealsay.service.auth;

import com.zeal.zealsay.common.constant.enums.UserStatus;
import com.zeal.zealsay.common.entity.SecuityUser;
import com.zeal.zealsay.common.entity.UserInfo;
import com.zeal.zealsay.common.entity.UserVo;
import com.zeal.zealsay.entity.User;
import com.zeal.zealsay.security.core.RedisTokenManager;
import com.zeal.zealsay.service.RoleService;
import com.zeal.zealsay.service.UserService;
import com.zeal.zealsay.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Slf4j
@Service(value = "userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    RedisTokenManager redisTokenManager;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserVo userVo = userService.userFind(s);
        if (userVo.getUser() != null) {
            return toSecuityUser(userVo.getUser());
        } else {
            throw new UsernameNotFoundException("该用户不存在");
        }
    }

    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHeader.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        SecuityUser user = (SecuityUser) loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }

    public SecuityUser getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof SecuityUser) {
            return (SecuityUser) principal;
        }
        return null;
    }

    /**
     * 刷新用户
     *
     * @param token
     */
    public void refreshUserToken(String token) throws Exception {
        UserInfo userInfo = redisTokenManager.getUserInfoByToken(token);
        redisTokenManager.delToken(userInfo);
        redisTokenManager.saveToken(userInfo);
    }

    /**
     * 用户退出登陆
     *
     * @param token
     */
    public void loginOff(String token) {
        UserInfo userInfo = redisTokenManager.getUserInfoByToken(token);
        redisTokenManager.delToken(userInfo);
    }

    /**
     * 转换成SecuityUser.
     *
     * @author zhanglei
     * @date 2019-09-12  15:12
     */
    public SecuityUser toSecuityUser(User user) {
        Collection<GrantedAuthority> grantedAuthorities = Collections
                .singleton(new SimpleGrantedAuthority(user.getRole().name()));
        SecuityUser secuityUser = new SecuityUser(user.getUsername(), user.getPassword(), UserStatus.NORMAL.equals(user.getStatus()),
                true, true,
                !UserStatus.LOCK.equals(user.getStatus()), grantedAuthorities);
        secuityUser.setAge(user.getAge());
        secuityUser.setAvatar(user.getAvatar());
        secuityUser.setSex(user.getSex());
        secuityUser.setUserId(user.getId());
        secuityUser.setStatus(user.getStatus());
        secuityUser.setLastPasswordResetDate(user.getLastPasswordResetDate());
        return secuityUser;
    }

    /**
     * 转换成SecuityUser.
     *
     * @author zhanglei
     * @date 2019-09-12  15:12
     */
    public SecuityUser toSecuityUser(UserInfo user) {
        Collection<GrantedAuthority> grantedAuthorities = Collections
            .singleton(new SimpleGrantedAuthority(user.getRole().name()));
        SecuityUser secuityUser = new SecuityUser(user.getUsername(), user.getPassword(), UserStatus.NORMAL.equals(user.getStatus()),
            true, true,
            !UserStatus.LOCK.equals(user.getStatus()), grantedAuthorities);
        secuityUser.setAge(user.getAge());
        secuityUser.setAvatar(user.getAvatar());
        secuityUser.setSex(user.getSex());
        secuityUser.setUserId(user.getId());
        secuityUser.setStatus(user.getStatus());
        secuityUser.setLastPasswordResetDate(user.getLastPasswordResetDate());
        return secuityUser;
    }

    /**
     * 获取当前登录信息.
     *
     * @author  zhanglei
     * @date 2020/1/13  12:17 下午
     */
    public UserInfo toUserInfo(Long id) {
        User user = userService.getById(id);
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user,userInfo);
        userInfo.setSignDate(LocalDateTime.now());
        return userInfo;
    }
}
