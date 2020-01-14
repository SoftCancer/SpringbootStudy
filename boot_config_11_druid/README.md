## 一、创建多模块项目

## 二、修改端口号
默认端口：8080，修改端口：9090
server.port=9090

修改为随机端口

server.port=${random.int(2048,99999)}

## 在boot_config_10_multi_datasource 的基础上配置 修改阿里的 Druid 连接池
### 步骤1： 配置application.properties 文件

1.新增 指定数据源类型
```aidl
spring.datasource.druid.slave.type=com.alibaba.druid.pool.DruidDataSource
```

2.修改配置文件，新增druid 关键字
```aidl
spring.datasource.druid.master.type=com.alibaba.druid.pool.DruidDataSource
spring.datasource.druid.master.jdbc-url=jdbc:mysql://192.168.1.107:3306/SpringBootMaster?useUnicode=true&characterEncoding=utf-8&useSSL=false
spring.datasource.druid.master.username=root
spring.datasource.druid.master.password=123456
spring.datasource.druid.master.max-wait-millis=10000
```
注：druid 连接池需要改 :spring.datasource.druid.master.jdbc-url --> spring.datasource.druid.master.url


3.修改数据源配置类 MybatisMasterConfiguration.java 和 MybatisSlaveConfiguration.java 类中的方法：
```aidl

    // 指定 master 数据库的 数据源配置
    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = MASTER_PREFIX)
    @Primary //优先方案，被注解的实现，优先被注入
    public DataSource masterDataSource(){
//        return DataSourceBuilder.create().build();  默认数据连接池
        return new DruidDataSource(); // 指定为Druid 连接池
    }

```
把 DataSourceBuilder.create().build();  改为：new DruidDataSource(); 

## 实现 数据库链接池druid监控平台的配置
1. 新增druid监控平台 的配置类
```aidl
package com.dongl.boot_config_11_druid.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 实现 数据库链接池druid监控平台的配置
 *  http://localhost:9090/druid/api.html
 *  username:druid
 *  password:123456
 * @author: YaoGuangXun
 * @date: 2020/1/13 22:56
 */
@Configuration
public class DruidConfig {

    @Bean
    public ServletRegistrationBean statViewServlet() {
        //创建servlet注册实体
        // 1.StatViewServlet是druid实现的一个Servlet容器，来显示druid的一些信息
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //设置ip白名单
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        //设置ip黑名单，如果allow与deny共同存在时,deny优先于allow
        servletRegistrationBean.addInitParameter("deny", "192.168.0.107");
        //设置控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", "druid");
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        //是否可以重置数据
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    //设置网站的过滤信息
    @Bean
    public FilterRegistrationBean statFilter() {
        //创建过滤器
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //设置过滤器过滤路径
        filterRegistrationBean.addUrlPatterns("/*");
        //忽略过滤的形式
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}

```

2. 启动访问：
```aidl
http://localhost:9090/druid/api.html
username:druid
password:123456
```

## 测试多数据源的数据变动操作
```aidl
        访问：http://localhost:9090/pay

    @RequestMapping(value = "/pay",method = RequestMethod.GET)
    public String pay(){
        payService.pay("38be0128e4f747eea0bd88c2ad37d0f7","57b49d1f64f94aca9b634d3d70f797a8",20);
        return "Success Save！";
    }

```
1.主要测试的是从一个库中的账户表 向另一个库中的 红包表转账，
在无异常的情况下，账户表会减少相应的金额，红包表会增加相应的金额。

但在出现异常的情况下，会出现数据的不一致性，现象：账户表为减少金额但红包表却增加了。

此种错误属于事务问题，将在下节boot_config_12_atomikos 中进行全局事务处理。
用atomikos 框架进行全局事务管理。