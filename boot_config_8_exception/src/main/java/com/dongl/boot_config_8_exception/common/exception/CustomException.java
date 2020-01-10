package com.dongl.boot_config_8_exception.common.exception;

import com.dongl.boot_config_8_exception.enums.ResultCodeEunm;
import lombok.Data;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2020/1/10 22:19
 */
@Data
public class CustomException extends RuntimeException {

    protected  Integer code;

    protected String msg;

    public CustomException(ResultCodeEunm resultCodeEunm) {
        this.code = resultCodeEunm.getCode();
        this.msg = resultCodeEunm.getMsg();
    }
}
