package com.zeal.zealsay.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import static com.zeal.zealsay.common.constant.enums.ResultCode.INTERNAL_SERVER_ERROR;
import static com.zeal.zealsay.common.constant.enums.ResultCode.OK;


/**
 * @author zeal
 * @version 1.0.0
 * @description 统一响应返回结果集
 * @date 2018-04-05 15:58
 */
@ApiModel(value = "统一响应对象")
@Data
@Builder
@AllArgsConstructor
public class Result<T> {

  @ApiModelProperty(value = "响应码",example = "200")
  private String code;

  @ApiModelProperty(value = "请求message",example = "请求成功")
  private String message;

  @ApiModelProperty(value = "响应内容")
  private T data;

  public Result() {
    super();
    this.code = OK.getCode();
    this.message = OK.getMessage();
  }

  public Result(String code, String message) {
    this.code = code;
    this.message = message;
    this.data = null;
  }

  public Result(T data) {
    this.data = data;
    this.code = OK.getCode();
    this.code = OK.getMessage();
  }

  public static Result of(Object data) {
    return Result.builder()
        .code(OK.getCode())
        .message(OK.getMessage())
        .data(data)
        .build();
  }

  public static Result ok() {
    return Result.builder()
        .code(OK.getCode())
        .message(OK.getMessage())
        .data("ok")
        .build();
  }

  public static Result serverError() {
    return Result.builder()
        .code(INTERNAL_SERVER_ERROR.getCode())
        .message(INTERNAL_SERVER_ERROR.getMessage())
        .data("INTERNAL_SERVER_ERROR")
        .build();
  }
}
