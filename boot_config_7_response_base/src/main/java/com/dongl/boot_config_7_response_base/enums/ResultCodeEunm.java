package com.dongl.boot_config_7_response_base.enums;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2020/1/10 15:00
 */
public enum ResultCodeEunm {

    /* 成功状态码 */
    SUCCESS(0,"成功"),

    /* 系统错误 */
    SYSTEM_ERROR(10000,"系统异常，稍后重试"),

    /* 参数错误 10001 - 19999 */
    PAEAM_IS_INVALIS(10001,"参数无效"),
    /* 用户错误 20001 - 29999 */
    USER_EXISTED(20001,"用户名已存在！"),
    USER_NOT_FIND(20002,"用户名不存在！") ;
    private Integer code;

    private String msg;

    ResultCodeEunm(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg(){
        return this.msg;
    }
}
