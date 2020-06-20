package com.zeal.zealsay.exception;

import com.zeal.zealsay.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static com.zeal.zealsay.common.constant.enums.ResultCode.*;

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
        e.printStackTrace();
        log.error("捕获异常 code : {},异常信息为 {}",exception.getCode(),e.getMessage());
        return Result.builder()
            .code(exception.getCode())
            .message(e.getMessage())
            .build();
    }

    /**
    * 访问拒绝异常.
    *
    * @author  zeal
    * @date 2019/7/28 12:24
    */
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.OK)     //权限不足异常
    public Result handleAccessDeniedException(Exception e, WebRequest request, AccessDeniedException exception){
        e.printStackTrace();
        log.error("捕获异常 code : {},异常信息为 {}",ACCESS_DENIED.getCode(),e.getMessage());
        return Result.builder()
            .code(ACCESS_DENIED.getCode())
            .message(ACCESS_DENIED.getMessage())
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
        e.printStackTrace();
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
        e.printStackTrace();
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
        e.printStackTrace();
        log.error("捕获异常 code : {},异常信息为 {}",INTERNAL_SERVER_ERROR.getCode(),e.getMessage());
        return Result.builder()
            .code(INTERNAL_SERVER_ERROR.getCode())
            .message(INTERNAL_SERVER_ERROR.getMessage())
            .build();
    }


}
