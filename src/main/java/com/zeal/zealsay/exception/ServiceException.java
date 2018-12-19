package com.zeal.zealsay.exception;

import lombok.Data;

import static com.zeal.zealsay.common.constant.enums.ResultCode.INTERNAL_SERVER_ERROR;

/**
 *@description 自定义封装异常类
 *@author  zeal
 *@date  2018-04-23  9:26
 *@version 1.0.0
 */
@Data
public class ServiceException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String code;

    public ServiceException(){

    }
    public ServiceException(String message){
        super(message);
        this.code = INTERNAL_SERVER_ERROR.getCode();
    }

    public ServiceException(String code, String message){
        super(message);
        this.code = code;
    }
}
