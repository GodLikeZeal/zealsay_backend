package com.zeal.zealsay;

import com.zeal.zealsay.service.EmailService;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JasyptEncryptorTest extends ZealsayApplicationTests{

  @Autowired
  EmailService emailService;

  @Test
  public void testEncrypt() throws Exception {
    PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
    SimpleStringPBEConfig config = new SimpleStringPBEConfig();
    config.setPassword("password");
    config.setAlgorithm("PBEWithMD5AndDES");
    config.setKeyObtentionIterations("1000");
    config.setPoolSize("1");
    config.setProviderName("SunJCE");
    config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
    config.setStringOutputType("base64");
    encryptor.setConfig(config);
    String plainText = "test";
    String encryptedText = encryptor.encrypt(plainText);
    System.out.println(encryptedText);
    Assert.assertNotNull(encryptedText);
  }

  @Test
  public void testDe() throws Exception {
    PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
    SimpleStringPBEConfig config = new SimpleStringPBEConfig();
    config.setPassword("password");
    config.setAlgorithm("PBEWithMD5AndDES");
    config.setKeyObtentionIterations("1000");
    config.setPoolSize("1");
    config.setProviderName("SunJCE");
    config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
    config.setStringOutputType("base64");
    encryptor.setConfig(config);
    String encryptedText = "Ftw+Zk1K1TlGOwNsKDbYiQ==";
    String plainText = encryptor.decrypt(encryptedText);
    Assert.assertEquals("test",plainText);
  }

  public void testSendEmail() throws Exception {
    //发送简单邮件
//    emailService.sendSimpleMail("zhangleifor@163.com","标题","内容");
    //发送html邮件
        String content = "<html>\n" +
        "<body><h2>html邮件内容</h2></body>" +
        "</html>";
    emailService.sendHtmlMail("xxxx@zealsay.com","账号注册激活邮件",content);
//    //发送附件邮件示例
//    emailService.sendAttachmentsMail("117***86@qq.com","给你的",content,"C:\\Users\\Administrator\\Pictures\\999.jpg");
//
//    //发送图片邮件示例
//    String resId ="id001";
//    String content = "<html>\n" +
//        "<body><h2>html邮件内容</h2><br><img src=\'cid:"+resId+"\'></img></body>" +
//        "</html>";
//    //发图片邮件
//    emailService.sendInlinResourceMail("ai*****2@126.com","标题",
//        content,"C:\\Users\\Administrator\\Pictures\\\\999.jpg",resId);
  }
}
