package com.zeal.zealsay.exception;

import com.zeal.zealsay.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.UnexpectedTypeException;

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
    public ResponseEntity<Result> handleServiceException(Exception e, WebRequest request, ServiceException exception){
        return ResponseEntity.ok(Result.builder()
            .code(exception.getCode())
            .message(e.getMessage())
            .build());
    }

    /**
     *@description 参数校验失败时异常
     *@author zeal
     *@date 2018-05-09 20:07
     *@version 1.0.0
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Result> handleMethodArgumentException(Exception e, WebRequest request){
        MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
        StringBuffer sb = new StringBuffer();
        exception.getBindingResult().getAllErrors()
            .forEach(s -> sb.append(s.getDefaultMessage()).append(";"));
        return ResponseEntity.ok(Result.builder()
            .code(METHOD_ARGUMENT_NOT_VALID.getCode())
            .message(sb.toString())
            .build());
    }

    /**
     *@description 参数校验失败时异常
     *@author zeal
     *@date 2018-05-09 20:07
     *@version 1.0.0
     */
    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<Result> handleBindException(Exception e, WebRequest request){
        BindException exception = (BindException) e;
        StringBuffer sb = new StringBuffer();
        exception.getBindingResult().getAllErrors()
            .forEach(s -> sb.append(s.getDefaultMessage()).append(";"));
        return ResponseEntity.ok(Result.builder()
            .code(METHOD_ARGUMENT_NOT_VALID.getCode())
            .message(sb.toString())
            .build());
    }

    /**
     *@description 系统运行时异常
     *@author zeal
     *@date 2018-05-09 20:07
     *@version 1.0.0
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Result> handleRuntimeException(Exception e, WebRequest request){
        return ResponseEntity.ok(Result.builder()
            .code(INTERNAL_SERVER_ERROR.getCode())
            .message(INTERNAL_SERVER_ERROR.getMessage())
            .build());
    }


}
