package com.zeal.zealsay.common.third.qiniu;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 七牛云对象云存储配置类.
 *
 * @author  zhanglei
 * @date 2019-03-15  15:17
 */
@Configuration
public class QiniuyunConfig {

  /**
   * 七牛云的 AccessKey.
   */
  @Value("${qiniu.AccessKey}")
  private String accessKey;

  /**
   * 七牛云的 SecretKey.
   */
  @Value("${qiniu.SecretKey}")
  private String secretKey;


  /**
   * 配置机房，根据自己的空间来选择，这里选择华北.
   */
  @Bean
  public com.qiniu.storage.Configuration qiniuConfig() {
    return new com.qiniu.storage.Configuration(Zone.zone1());
  }

  /**
   * 构建一个七牛上传工具实例.
   */
  @Bean
  public UploadManager uploadManager() {
    return new UploadManager(qiniuConfig());
  }

  /**
   * 认证信息实例.
   */
  @Bean
  public Auth auth() {
    return Auth.create(accessKey, secretKey);
  }

  /**
   * 构建七牛空间管理实例.
   */
  @Bean
  public BucketManager bucketManager() {
    return new BucketManager(auth(), qiniuConfig());
  }

  @Bean
  public Gson gson() {
    return new Gson();
  }

}
