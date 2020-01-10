package com.dongl.boot_config_7_response_base.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dongl.boot_config_7_response_base.enums.CommonErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @description: 比较完整的返回格式，和CommonErrorCode 枚举类暂时没有使用
 * @author: YaoGuangXun
 * @date: 2020/1/10 17:29
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResultMsg<T> {
    /**
     * 是否成功
     */
    private Boolean succ;

    /**
     * 服务器当前时间戳
     */
    private Long ts = System.currentTimeMillis();

    /**
     * 成功数据
     */
    private T data;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误描述
     */
    private String msg;

    public static ResultMsg ofSuccess() {
        ResultMsg result = new ResultMsg();
        result.succ = true;
        return result;
    }

    public static ResultMsg ofSuccess(Object data) {
        ResultMsg result = new ResultMsg();
        result.succ = true;
        result.setData(data);
        return result;
    }

    public static ResultMsg ofFail(String code, String msg) {
        ResultMsg result = new ResultMsg();
        result.succ = false;
        result.code = code;
        result.msg = msg;
        return result;
    }

    public static ResultMsg ofFail(String code, String msg, Object data) {
        ResultMsg result = new ResultMsg();
        result.succ = false;
        result.code = code;
        result.msg = msg;
        result.setData(data);
        return result;
    }

    public static ResultMsg ofFail(CommonErrorCode resultEnum) {
        ResultMsg result = new ResultMsg();
        result.succ = false;
        result.code = resultEnum.getCode();
        result.msg = resultEnum.getMessage();
        return result;
    }

    /**
     * 获取 json
     */
    public String buildResultJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("succ", this.succ);
        jsonObject.put("code", this.code);
        jsonObject.put("ts", this.ts);
        jsonObject.put("msg", this.msg);
        jsonObject.put("data", this.data);
        return JSON.toJSONString(jsonObject, SerializerFeature.DisableCircularReferenceDetect);
    }
}
