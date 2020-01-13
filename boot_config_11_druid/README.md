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


