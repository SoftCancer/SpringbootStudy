## 一、创建多模块项目

## 二、修改端口号
默认端口：8080，修改端口：9090
server.port=9090

修改为随机端口

server.port=${random.int(2048,99999)}

## 三、自定义属性配置
在application.properties 文件中配置属性，在类中通过 @Value 获取。
    @Value("${boot.msg}") <br>
    private String msg;

    @GetMapping("/msg")
    public String getMsg(){
        return msg;
    }
 
 ## 三、springboot实现日志
 
 1.logback，log4j :是日志实现框架，就是实现怎么记录日志。<br>
 2.slf4j :提供了java中所有的日志框架的简单抽象，就是日志的API，它不能单独使用，
 故：必须结合logback或log4j日志框架来实现。
 
 3.Springboot 2.0默认采用slf4j+logback的日志搭建。
 ### 为什么控制台只输出 info，warn，error ？
 因为Springboot 默认只输出info级别
 
 设置输出级别 
 logging:
   level:
     com.dongl.boot_config_log: trace
     
 4.日志的存储路径和文件名
   把日志输出到 D:/logData/springboot/springboot.log
   file: /log/springboot.log
   pattern:
     console: '%d{yyyy-MM-dd} %-5level %logger{50} - %msg%n'
 