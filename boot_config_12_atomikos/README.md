## 一、创建多模块项目

## 二、修改端口号
默认端口：8080，修改端口：9090
server.port=9090

修改为随机端口

server.port=${random.int(2048,99999)}

## 在boot_config_11_multi_datasource 的基础上 解决分布式事务问题
### 步骤1： 在 pom.xml 中添加依赖
```aidl
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jta-atomikos</artifactId>
</dependency>
```


### 步骤1： 配置application.properties 文件
在配置文件中添加
```aidl
spring.datasource.druid.master.minPoolSize = 3
spring.datasource.druid.master.maxPoolSize = 25
spring.datasource.druid.master.maxLifetime = 20000
spring.datasource.druid.master.borrowConnectionTimeout = 30
spring.datasource.druid.master.loginTimeout = 30
spring.datasource.druid.master.maintenanceInterval = 60
spring.datasource.druid.master.maxIdleTime = 60
```