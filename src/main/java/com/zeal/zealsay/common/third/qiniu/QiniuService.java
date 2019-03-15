package com.zeal.zealsay.common.third.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;

/**
 * 七牛云对象云存储服务.
 *
 * @author  zhanglei
 * @date 2019-03-15  15:55
 */
@Component
public class QiniuService implements InitializingBean {

  @Autowired
  UploadManager uploadManager;
  @Autowired
  BucketManager bucketManager;
  @Autowired
  Auth auth;


  @Value("${qiniu.Bucket}")
  private String bucket;

  /**
   * 定义七牛云上传的相关策略.
   *
   * @author  zhanglei
   * @date 2019-03-15  16:00
   */
  private StringMap putPolicy;

  public Response uploadFile(File file) throws QiniuException {
    Response response = this.uploadManager.put(file, null, getUploadToken());
    int retry = 0;
    while (response.needRetry() && retry < 3) {
      response = this.uploadManager.put(file, null, getUploadToken());
      retry++;
    }
    return response;
  }

  /**
   * 以流的形式上传.
   *
   * @author  zhanglei
   * @date 2019-03-15  15:59
   */
  public Response uploadFile(InputStream inputStream) throws QiniuException {
    Response response = this.uploadManager.put(inputStream, null, getUploadToken(), null, null);
    int retry = 0;
    while (response.needRetry() && retry < 3) {
      response = this.uploadManager.put(inputStream, null, getUploadToken(), null, null);
      retry++;
    }
    return response;
  }

  /**
   * 删除七牛云上的相关文件.
   *
   * @author  zhanglei
   * @date 2019-03-15  15:59
   */
  public Response delete(String key) throws QiniuException {
    Response response = bucketManager.delete(this.bucket, key);
    int retry = 0;
    while (response.needRetry() && retry++ < 3) {
      response = bucketManager.delete(bucket, key);
    }
    return response;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    this.putPolicy = new StringMap();
    putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
  }

  /**
   * 获取上传凭证.
   *
   * @author  zhanglei
   * @date 2019-03-15  15:59
   */
  private String getUploadToken() {
    return this.auth.uploadToken(bucket, null, 3600, putPolicy);
  }
}
