package com.zeal.zealsay.common.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 系统参数.
 *
 * @author  zhanglei
 * @date 2019-09-27  15:35
 */
@Data
@Component
public class SystemConstants {

  @Value("${web.domain}")
  private String domain;

  @Value("${web.name}")
  private String name;

  @Value("${web.default.password}")
  private String defaultPassword;

  @Value("${qiniu.Bucket}")
  private String qiniuBucket;

  @Value("${qiniu.Domain}")
  private String qiniuDomain;
}
