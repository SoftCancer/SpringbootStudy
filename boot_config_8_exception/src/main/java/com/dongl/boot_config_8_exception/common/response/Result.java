package com.dongl.boot_config_8_exception.common.response;

import com.dongl.boot_config_8_exception.enums.ResultCodeEunm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2020/1/10 15:20
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> {

    /**
     * 1. status：状态值，代表请求的状态结果
     **/
    private Integer status;
    /**
     * 2.response:描述，对本次状态码的描述
     **/
    private String desc;
    /**
     * 3.data：数据，本次返回的数据
     **/
    private T data;


    public static Result success(){
        Result result = new Result();
        // result.setStatus(ResultCodeEunm.SUCCESS.getCode());
        result.setResultCode(ResultCodeEunm.SUCCESS);
        return result;
    }

    public static Result success(Object data){
        Result result = new Result();
        result.setStatus(ResultCodeEunm.SUCCESS.getCode());
        result.setData(data);
        return result;
    }

    /**
     * 失败，指定status，desc
     **/
    public static Result fail(Integer status,String desc){
        Result result = new Result();
        result.setStatus(status);
        result.setDesc(desc);
        return result;
    }

    public static Result fail(ResultCodeEunm resultCodeEunm){
        Result result = new Result();
        result.setResultCode(resultCodeEunm);
        return result;
    }

    /**
     * 把枚举类型转化为 Result
     * @Author YaoGuangXun
     * @Date 15:35 2020/1/10
     **/
    private void setResultCode(ResultCodeEunm resultCode){
        this.status = resultCode.getCode();
        this.desc = resultCode.getMsg();
    }

}
