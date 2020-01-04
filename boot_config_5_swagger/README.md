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
  
## SpringBoot 整合Swagger2 
1. 添加依赖包

       <!-- swagger2 依赖包 -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>${swagger.version}</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>${swagger.version}</version>
        </dependency>

 2. 在application.properties 配置文件中添加配置
 
        spring.swagger2.enabled=true


 3. 创建Swagger2 的配置类
 
        /**
         * 通过 @Configuration注解，让Spring来加载该类配置。
         * 再通过 @EnableSwagger2注解来启用Swagger2。
         * @description: 配置Swagger API
         * @date: 2020/1/1 16:09
         */
        @Configuration
        @EnableSwagger2
        public class SwaggerConfig  {
        
            @Value(value = "${spring.swagger2.enabled}")
            private Boolean swaggerEnable;
        
            @Bean
            public Docket createSwaggerApi(){
                return new Docket(DocumentationType.SWAGGER_2)
                        // apiInfo()用来创建该Api的基本信息
                        .apiInfo(apiInfo())
                        .enable(swaggerEnable)
                        // select()函数返回一个ApiSelectorBuilder实例用来控制哪些接口暴露给Swagger来展现，
                        // 本例采用指定扫描的包路径来定义，Swagger会扫描该包下所有Controller定义的API
                        .select()
                        .apis(RequestHandlerSelectors.basePackage("com.dongl.boot_config_swagger"))
                        .paths(PathSelectors.any())
                        .build();
            }
        
            private ApiInfo apiInfo(){
                return new ApiInfoBuilder()
                        .title("东朗教育网站API")
                        .description("网站开发接口Api")
                        .termsOfServiceUrl("192.168.1.107")
                        .contact(new Contact("程序员Dongl","192.168.1.107","qq.com"))
                        .version("1.0")
                        .build();
            }
        }

 