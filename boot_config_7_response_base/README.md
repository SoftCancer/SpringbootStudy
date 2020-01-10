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
