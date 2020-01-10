package com.dongl.boot_config_8_exception.common;

import com.dongl.boot_config_8_exception.enums.ResultCodeEunm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 捕获运行时异常，并把异常统一封装为 ErrorResult 对象
 * @author: YaoGuangXun
 * @date: 2020/1/10 21:13
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理运行时异常
     * @Author YaoGuangXun
     * @Date 21:49 2020/1/10
     **/
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ErrorResult handleThrowable(Throwable e, HttpServletRequest request){
      ErrorResult errorResult =  ErrorResult.fail(ResultCodeEunm.SYSTEM_ERROR,e);
      log.info("URL:{},系统异常;",request.getRequestURI(),e);
      return errorResult;
    }

}
