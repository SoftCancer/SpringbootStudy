package com.dongl.boot_config_7_response_base.common;

import com.alibaba.fastjson.JSON;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @description:  对返回数据 进行统一拦截处理
 * 参考：https://www.jianshu.com/p/61ed9a030460
 * @author: YaoGuangXun
 * @date: 2020/1/10 16:38
 */
@ControllerAdvice(basePackages = "com.dongl.boot_config_7_response_base")//要扫描的包
public class CommonResponseDataAdvice implements ResponseBodyAdvice {

    /**
     * 用于判断是否需要做处理。
     * @Author YaoGuangXun
     * @Date 16:39 2020/1/10
     **/
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object obj, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // o is null -> return response
        if (obj == null) {
            return Result.success();
        }
        // o is instanceof ConmmonResponse -> return o
        if (obj instanceof Result) {
            return (Result<Object>) obj;
        }
        // string 特殊处理
        if (obj instanceof String) {
            return JSON.toJSON(Result.success(obj));
        }
        return Result.success(obj);
    }
}
