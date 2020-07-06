package com.zeal.zealsay.service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.zeal.zealsay.common.constant.SystemConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


/**
 * 短信服务.
 *
 * @author zhanglei
 * @date 2020/6/26 11:00
 */
@Slf4j
@Service
public class SmsService {

    @Autowired
    SystemConstants systemConstants;
    @Autowired
    DictService dictService;
    @Autowired
    IAcsClient client;

    // 设置鉴权参数，初始化客户端

    @Value("sms.templateCode")
    private String templateCode;

    @Value("sms.signName")
    private String signName;

    @Bean("client")
    public IAcsClient client() {
        DefaultProfile profile =
        DefaultProfile.getProfile(
                systemConstants.getSmsRegionId(),// 地域ID
                systemConstants.getSmsAccessKey(),// 您的AccessKey ID
                systemConstants.getSmsAccessSecret());// 您的AccessKey Secret
        return new DefaultAcsClient(profile);
    }
    /**
     * 发送验证码
     *
     * @param phone 手机号
     * @param code  验证码
     * @throws ClientException
     */
    @Async
    public void send(String phone, String code) throws ClientException {
        CommonRequest request = new CommonRequest();
        // 接收短信的手机号码
        request.putQueryParameter("PhoneNumbers", phone);
        // 短信签名名称。请在控制台签名管理页面签名名称一列查看（必须是已添加、并通过审核的短信签名）。
        request.putQueryParameter("SignName", signName);
        // 短信模板ID
        request.putQueryParameter("TemplateCode", templateCode);
        // 短信模板变量对应的实际值，JSON格式。
        request.putQueryParameter("TemplateParam", "{\"code\":" + code + "}");
        CommonResponse commonResponse = client.getCommonResponse(request);
        String data = commonResponse.getData();
        String sData = data.replaceAll("'\'", "");
        log.info("sendSms", sData);
    }


    /**
     * 查询发送详情
     */
    private void querySendDetails(String bizId) throws ClientException {
        CommonRequest request = new CommonRequest();
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("QuerySendDetails");
        // 接收短信的手机号码
        request.putQueryParameter("PhoneNumber", "156xxxxxxxx");
        // 短信发送日期，支持查询最近30天的记录。格式为yyyyMMdd，例如20191010。
        request.putQueryParameter("SendDate", "20191010");
        // 分页记录数量
        request.putQueryParameter("PageSize", "10");
        // 分页当前页码
        request.putQueryParameter("CurrentPage", "1");
        // 发送回执ID，即发送流水号。
        request.putQueryParameter("BizId", bizId);
        CommonResponse response = client.getCommonResponse(request);
        log.info("querySendDetails", response.getData());
    }

}