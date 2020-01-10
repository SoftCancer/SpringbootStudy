package com.dongl.boot_config_8_exception.common.exception;

import com.dongl.boot_config_8_exception.enums.ResultCodeEunm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2020/1/10 21:26
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResult {

    private Integer status;
    /**
     * 提示异常信息
     **/
    private String msg;
    /**
     * 异常信息的名称
     **/
    private String exception;
    /**
     * 异常堆栈信息
     **/
    private String errors;

    public static ErrorResult fail(ResultCodeEunm resultCodeEunm,Throwable e,String msg){
        ErrorResult errorResult = fail(resultCodeEunm,e);
        errorResult.setMsg(msg);
        return errorResult;
    }

    /**
     * 对异常枚举进行封装
     * @Author YaoGuangXun
     * @Date 21:44 2020/1/10
     **/
    public static ErrorResult fail(ResultCodeEunm resultCodeEunm ,Throwable e){
        ErrorResult errorResult = new ErrorResult();
        errorResult.setMsg(resultCodeEunm.getMsg());
        errorResult.setStatus(resultCodeEunm.getCode());
        errorResult.setException(e.getClass().getName());
//        errorResult.setErrors(Throwables.getStackTraceAsString(e));
        return errorResult;
    }

}
