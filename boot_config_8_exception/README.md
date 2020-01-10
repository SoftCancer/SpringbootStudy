## 一、创建多模块项目

## 二、修改端口号
默认端口：8080，修改端口：9090
server.port=9090

修改为随机端口

server.port=${random.int(2048,99999)}


## 三、定义标准数据返回格式基础版

一般的response 的返回格式包含3部分：
    
    1.status：状态值，代表请求的状态结果
    2.response:描述，对本次状态码的描述
    3.data：数据，本次返回的数据
    {
        "status":200,
        "desc":"成功",
        "data"："success"
    }
 

实现基础版数据返回格式 

## 高级程序员对response 的封装。
因为每写一个接口，都需要手动指定Result.success() 的返回格式代码，比较繁杂。
因此进行优化，优化目标：不要每个接口手动指定Result 返回格式。

### 采用ResponseBodyAdvice 技术来实现Response 的统一格式

1. pom.xml文件中添加 fastjson jar包
    
       <dependency>
           <groupId>com.alibaba</groupId>
           <artifactId>fastjson</artifactId>
           <version>1.2.62</version>
       </dependency>
    
2.重写ResponseBodyAdvice 方法

    package com.dongl.boot_config_7_response_base.common;
    
    import com.alibaba.fastjson.JSON;
    import org.springframework.core.MethodParameter;
    import org.springframework.http.MediaType;
    import org.springframework.http.server.ServerHttpRequest;
    import org.springframework.http.server.ServerHttpResponse;
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


以上代码，有两个地方需要注意：

### 1. @ControllerAdvice注解
@ControllerAdvice 这个注解非常有用，他作为增强Controller 的扩展功能类，主要体现在2个方面：

1. 对Controller 全局数据统一处理，即目前所使用的功能，对返回值做统一封装。
2. 对Controller 全局异常统一处理，

使用@ControllerAdvice 时，还需要注意加上 basePackages = "com.dongl.boot_config_7_response_base"

如果不加的话，他可能对整个系统的Controller 做扩展功能，可能对某些功能产生冲突，

例如，不加的话，在使用Swagger 时会出现空白页。


### 2.beforeBodyWrite 方法体中做判断。

优化后的统一返回标准包含：CommonResponseDataAdvice.java ,Result.java ,
ResultCodeEunm 三个类。


## 四、全局异常
 
### 步骤1： 封装异常内容，统一存储在枚举中，给予标准返回值得内容

```$xslt
package com.dongl.boot_config_8_exception.enums;

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

```
 
### 步骤2： 封装Controller 的异常结果
```$xslt
package com.dongl.boot_config_8_exception.common;

import com.dongl.boot_config_8_exception.enums.ResultCodeEunm;
import com.google.common.base.Throwables;
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

```
### 全局异常处理器，对异常处理
```$xslt
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

```
 
 ### 步骤4：测试
 需要把 CommonResponseDataAdvice.java 注释掉。测试完在进行放开。
 

 
## 新增  自定义异常集成到全局异常
###步骤1：封装一个自定义异常
```$xslt
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

```

### 步骤2：把自定义异常 集成到 全局异常处理器中

全局异常处理器只要在GlobalExceptionHandler 类的基础上，添加一个自定义异常处理方法即可。
```$xslt
   * 处理自定义异常
     * @Author YaoGuangXun
     * @Date 22:25 2020/1/10
     **/
    @ExceptionHandler(CustomException.class)  // 处理某一类异常
    public ErrorResult handleCustomException(CustomException e, HttpServletRequest request){
        ErrorResult errorResult = ErrorResult.builder().status(e.code)
                .msg(e.msg)
                .exception(e.getClass().getName())
                .build();
        log.warn("URL:{},业务异常:{};",request.getRequestURI(),errorResult);
        return errorResult;
    }

```

### 步骤3：体验效果
```$xslt
 @GetMapping("/custom")
    public void custom() {
        throw new CustomException(ResultCodeEunm.USER_EXISTED);
    }
```
效果：
```$xslt
{ 
  "status":20001,
  "msg":"用户名已存在！",
  "exception":"com.dongl.boot_config_8_exception.common.exception.CustomException",
  "errors":null
}
```

## 把全局异常处理器 集成到接口返回值统一标准格式中
### 步骤1：在 CommonResponseDataAdvice 类的基础上新增判断

``` 
      // 用于全局异常处理统一返回
        if (obj instanceof ErrorResult){
            ErrorResult errorResult = (ErrorResult)obj;
            return Result.fail(errorResult.getStatus(),errorResult.getMsg());
        }
```
### 体验效果

```$xslt
{
    "status":10000,
    "desc":"系统异常，
    稍后重试","data":null
}
```
