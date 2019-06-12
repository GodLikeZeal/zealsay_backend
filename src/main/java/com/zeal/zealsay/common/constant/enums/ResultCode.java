package com.zeal.zealsay.common.constant.enums;


/**
 * 返回码.
 *
 * @author  zhanglei
 * @date 2018/10/27  11:12 AM
 */
public enum ResultCode {
  OK("200","请求成功"),
  FAILED("400","请求异常"),
  ACCESS_DENIED("401","您无权进行此操作"),
  FORBIDDEN("403","您无权访问该资源"),
  NOT_FOUND("404","找不到该页面"),
  INTERNAL_SERVER_ERROR("500","服务器异常"),
  BAD_GATEWAY("502","网关异常"),
  BAD_CREDENTIALS("000","用户名或密码不正确"),
  USERNAME_NOTFOUND("001","该账户不存在"),
  ACCOUNT_LOCKED("002","该账户被锁定"),
  NONCE_EXPIRED("003","该账户已失效"),
  ACCOUNT_DISABLED("004","该账户被禁用"),
  TOKEN_EXPIRED("005","token已过期"),
  METHOD_ARGUMENT_NOT_VALID("006","参数校验异常")
  ;
  private String code;
  private String message;

  ResultCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
