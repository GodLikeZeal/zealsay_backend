package com.zeal.zealsay.util;

/**
 * 简单加密工具类.
 *
 * @author zhanglei
 * @date 2019-09-27  15:29
 */
public class SimpleEncryptionUtil {
  /**
   * 加密password
   */
  private final static int SECRET = 1010;

  /**
   * 简单加密
   *
   * @param text 待加密字符
   * @return
   */
  public static String encrypt(String text) {
    byte[] bt = text.getBytes();
    for (int i = 0; i < bt.length; i++) {
      bt[i] = (byte) (bt[i] ^ SECRET);
    }
    return new String(bt, 0, bt.length);
  }

  /**
   * 简单解密
   *
   * @param text 待解密字符
   * @return
   */
  public static String dencrypt(String text) {
    byte[] bt = text.getBytes();
    for (int i = 0; i < bt.length; i++) {
      bt[i] = (byte) (bt[i] ^ SECRET);
    }
    return new String(bt, 0, bt.length);
  }
}
