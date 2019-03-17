package com.zeal.zealsay.service;

import com.zeal.zealsay.common.entity.SecuityUser;
import com.zeal.zealsay.entity.LoginLog;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.mapper.LoginLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeal.zealsay.util.IpAddressUtil;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * <p>
 * 登录记录表 服务实现类
 * </p>
 *
 * @author zhanglei
 * @since 2019-03-16
 */
@Slf4j
@Transactional(rollbackFor = {ServiceException.class,RuntimeException.class,Exception.class})
@Service
public class LoginLogService extends ServiceImpl<LoginLogMapper, LoginLog> {

    /**
    * 保存登录信息.
    *
    * @author  zeal
    * @date 2019/3/17 21:45
    */
    @Async
    public Boolean saveLog(HttpServletRequest request, SecuityUser user) {
        //保存记录
        final String agentString = request.getHeader("User-Agent");
        Optional<UserAgent> userAgent = Optional
                .ofNullable(UserAgent.parseUserAgentString(agentString));
        DeviceType deviceType = userAgent
                .map(u -> u.getOperatingSystem())
                .map(o -> o.getDeviceType())
                .orElse(DeviceType.UNKNOWN);
        //解析设备
        String device = analysisDevice(deviceType,agentString);
        //解析ip
        String ip = IpAddressUtil.getIpAdrress(request);
        return save(LoginLog.builder()
                .loginDate(LocalDateTime.now())
                .userId(user.getUserId())
                .username(user.getUsername())
                .device(device)
                .ip(ip)
                .build());
    }

    /**
    * 解析设备名称.
    *
    * @author  zeal
    * @date 2019/3/17 21:39
    */
    private String analysisDevice(DeviceType deviceType,String agentString) {
        switch (deviceType) {
            case COMPUTER:
                return "PC";
            case TABLET: {
                if (agentString.contains("Android")) {
                    return "Android Pad";
                }
                if (agentString.contains("iOS")) {
                    return "iPad";
                } else {
                    return "Unknown";
                }
            }
            case MOBILE: {
                if (agentString.contains("Android")) {
                    return "Android";
                }
                if (agentString.contains("iOS")) {
                    return "IOS";
                } else {
                    return "Unknown";
                }
            }
            default:
                return "Unknown";
        }
    }
}
