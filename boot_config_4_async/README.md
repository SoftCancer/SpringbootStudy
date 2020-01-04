## 一、创建多模块项目

## 二、修改端口号
默认端口：8080，修改端口：9090
server.port=9090

修改为随机端口

server.port=${random.int(2048,99999)}

## 三、自定义属性配置
在application.properties 文件中配置属性，在类中通过 @Value 获取。
    
    @Value("${boot.msg}")
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
     
 ## 四、lombok的使用
 1. 在idea中下载 lombok 插件
 
 2.在 pom.xml 文件中引入 lombok.jar
 
    <!-- lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.8</version>
    </dependency>
    
####  @data的使用
    在实体类中使用注解 @data 可以省略 get() set(）方法

    @Data
    public class CityVO {
        private String id ;
        private String name;
        private String zipCode;
    }
####  @slf4j的使用
    
   在类中使用注解@slf4j 可以省略
    
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
   声明，直接使用log对象输出日志。
     
     @RestController
     @Slf4j
     public class CityController {
         public CityVO findCity(){
             log.info("获取城市信息");
             CityVO cityVO = new CityVO();
             cityVO.setId("123");
             cityVO.setName("北京市");
             cityVO.setZipCode("1100123");
             return cityVO;
         }
     }
  
  ## 使用注解 @Async 创建一个自定义线程池
  
    @Configuration
    @EnableAsync
    public class ConfigAsync {

    /**
     * 自定义线程池
     * @return 
     * @Author YaoGuangXun
     * @Date 13:50 2020/1/1
     **/
    @Bean("threadPoolTaskExecutorOne")
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutorOne(){
        // 1. 创建线程实例
        ThreadPoolTaskExecutor poolTaskExecutor= new ThreadPoolTaskExecutor();
        // 1. 线程池的核心数量（最小线程数）
        poolTaskExecutor.setCorePoolSize(10);
        // 1. 维护线程最大数量
        poolTaskExecutor.setMaxPoolSize(50);
        // 1.阻塞任务队列的大小
        poolTaskExecutor.setQueueCapacity(30);
        // 1.部分线程空闲最大存活时间，默认存活时间是60s
        poolTaskExecutor.setKeepAliveSeconds(150);
        // 1.线程名
        poolTaskExecutor.setThreadNamePrefix("Integral-1");
        // 1.表示线程池的饱和策略。
        // 如果阻塞队列满了并且没有空闲的线程，这时如果继续提交任务，就需要采取一种策略处理该任务。
        // 线程池提供了4种策略：
        //  AbortPolicy：直接抛出异常，这是默认策略；
        //  CallerRunsPolicy：用调用者所在的线程来执行任务；
        //  DiscardOldestPolicy：丢弃阻塞队列中靠最前的任务，并执行当前任务；
        //  DiscardPolicy：直接丢弃任务；
        poolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 1.
        poolTaskExecutor.initialize();
        return poolTaskExecutor;
    }

 
 