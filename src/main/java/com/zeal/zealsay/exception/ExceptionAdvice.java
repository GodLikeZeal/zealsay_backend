package com.zeal.zealsay.exception;

import com.zeal.zealsay.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;


import static com.zeal.zealsay.common.constant.enums.ResultCode.INTERNAL_SERVER_ERROR;
import static com.zeal.zealsay.common.constant.enums.ResultCode.METHOD_ARGUMENT_NOT_VALID;

/**
 *@description 自定义异常切面
 *@author  zeal
 *@date  2018-04-23  9:53
 *@version 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {


    /**
     *@description 自定义业务异常
     *@author zeal
     *@date 2018-05-09 20:07
     *@version 1.0.0
     */
    @ExceptionHandler(value = ServiceException.class)
    @ResponseStatus(value = HttpStatus.OK)     //服务异常
    public Result handleServiceException(Exception e, WebRequest request, ServiceException exception){
        log.error("捕获异常 code : {},异常信息为 {}",exception.getCode(),e.getMessage());
        return Result.builder()
            .code(exception.getCode())
            .message(e.getMessage())
            .build();
    }

    /**
     *@description 参数校验失败时异常
     *@author zeal
     *@date 2018-05-09 20:07
     *@version 1.0.0
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handleMethodArgumentException(Exception e, WebRequest request){
        MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
        StringBuffer sb = new StringBuffer();
        exception.getBindingResult().getAllErrors()
            .forEach(s -> sb.append(s.getDefaultMessage()).append(";"));
        log.error("捕获异常 code : {},异常信息为 {}",METHOD_ARGUMENT_NOT_VALID.getCode(),e.getMessage());
        return Result.builder()
            .code(METHOD_ARGUMENT_NOT_VALID.getCode())
            .message(sb.toString())
            .build();
    }

    /**
     *@description 参数校验失败时异常
     *@author zeal
     *@date 2018-05-09 20:07
     *@version 1.0.0
     */
    @ExceptionHandler(value = BindException.class)
    public Result handleBindException(Exception e, WebRequest request){
        BindException exception = (BindException) e;
        StringBuffer sb = new StringBuffer();
        exception.getBindingResult().getAllErrors()
            .forEach(s -> sb.append(s.getDefaultMessage()).append(";"));
        log.error("捕获异常 code : {},异常信息为 {}",METHOD_ARGUMENT_NOT_VALID.getCode(),e.getMessage());
        return Result.builder()
            .code(METHOD_ARGUMENT_NOT_VALID.getCode())
            .message(sb.toString())
            .build();
    }

    /**
     *@description 系统运行时异常
     *@author zeal
     *@date 2018-05-09 20:07
     *@version 1.0.0
     */
    @ExceptionHandler(value = Exception.class)
    public Result handleRuntimeException(Exception e, WebRequest request){
        log.error("捕获异常 code : {},异常信息为 {}",INTERNAL_SERVER_ERROR.getCode(),e.getMessage());
        return Result.builder()
            .code(INTERNAL_SERVER_ERROR.getCode())
            .message(INTERNAL_SERVER_ERROR.getMessage())
            .build();
    }


}
