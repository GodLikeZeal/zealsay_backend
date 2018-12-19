package com.zeal.zealsay.exception;

import com.zeal.zealsay.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
@ControllerAdvice
public class ExceptionAdvice {


    /**
     *@description 自定义业务异常
     *@author zeal
     *@date 2018-05-09 20:07
     *@version 1.0.0
     */
    @ResponseBody
    @ExceptionHandler(value = ServiceException.class)
    @ResponseStatus(value = HttpStatus.OK)     //服务异常
    public ResponseEntity<Result> handleServiceException(Exception e, WebRequest request, ServiceException exception){
        return ResponseEntity.ok(Result.builder()
            .code(exception.getCode())
            .data(e.getMessage())
            .build());
    }

    /**
     *@description 参数校验失败时异常
     *@author zeal
     *@date 2018-05-09 20:07
     *@version 1.0.0
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Result> handleMethodArgumentException(Exception e, WebRequest request){
        return ResponseEntity.ok(Result.builder()
            .code(METHOD_ARGUMENT_NOT_VALID.getCode())
            .data(e.getMessage())
            .build());
    }

    /**
     *@description 系统运行时异常
     *@author zeal
     *@date 2018-05-09 20:07
     *@version 1.0.0
     */
    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Result> handleRuntimeException(Exception e, WebRequest request){
        return ResponseEntity.ok(Result.builder()
            .code(INTERNAL_SERVER_ERROR.getCode())
            .data(INTERNAL_SERVER_ERROR.getMessage())
            .build());
    }


}
