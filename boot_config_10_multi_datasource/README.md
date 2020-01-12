## 一、创建多模块项目

## 二、修改端口号
默认端口：8080，修改端口：9090
server.port=9090

修改为随机端口

server.port=${random.int(2048,99999)}

## 在boot_config_9_mybatis 的基础上配置 多数据源
### 步骤1： 配置application.properties 文件
1.添加多数据源的配置
注意：在SpringBoot1.0 配置数据源的过程中主要是写成：spring.datasource.url 和spring.datasource.driverClassName。
   
   而在SpringBoot1.02.0升级之后需要变更成：spring.datasource.jdbc-url和spring.datasource.driver-class-name

```aidl

server.port=9090

#配置mapper.xml 的位置 ，多数据源中已在各自的 MybatisMasterConfiguration 配置文件中配置，因此可以注释掉。
#mybatis.mapper-locations=classpath*:com.dongl.boot_config_10_multi_datasource.dao.mapper/*.xml

## 显示sql日志
logging.level.com.dongl.boot_config_10_multi_datasource=debug

## 多数据源配置
# 主数据库,springboot 升级到2.0之后,配置多数据源时变更成：spring.datasource.jdbc-url和spring.datasource.driver-class-name
spring.datasource.master.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.master.jdbc-url=jdbc:mysql://192.168.1.107:3306/SpringBootMaster?useUnicode=true&characterEncoding=utf-8&useSSL=false
spring.datasource.master.username=root
spring.datasource.master.password=123456
spring.datasource.master.max-idle=30
spring.datasource.master.max-wait-millis=10000
spring.datasource.master.min-idle=5
spring.datasource.master.initial-size=5
spring.datasource.master.initSQL=SELECT 2
spring.datasource.master.connection-init-sqls=SELECT 1
spring.datasource.master.validation-query=SELECT 1
spring.datasource.master.validation-query-timeout=3000
#spring.datasource.master.test-on-borrow=true
spring.datasource.master.test-while-idle=true
spring.datasource.master.time-between-eviction-runs-millis=10000
spring.datasource.master.max-active=30

#从数据库
spring.datasource.slave.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.slave.jdbc-url=jdbc:mysql://192.168.1.107:3306/SpringBootSlave?useUnicode=true&characterEncoding=utf-8&useSSL=false
spring.datasource.slave.username=root
spring.datasource.slave.password=123456
spring.datasource.slave.max-idle=30
spring.datasource.slave.max-wait-millis=10000
spring.datasource.slave.min-idle=5
spring.datasource.slave.initial-size=5
spring.datasource.slave.initSQL=SELECT 2
spring.datasource.slave.connection-init-sqls=SELECT 1
spring.datasource.slave.validation-query=SELECT 1
spring.datasource.slave.validation-query-timeout=3000
#spring.datasource.slave.test-on-borrow=true
spring.datasource.slave.test-while-idle=true
spring.datasource.slave.time-between-eviction-runs-millis=10000
spring.datasource.slave.max-active=30

```

### 步骤2：配置数据源

1.配置SpringBootMaster 数据库的数据源连接
```aidl
package com.dongl.boot_config_10_multi_datasource.config;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2020/1/12 15:10
 */

@Configuration
@EnableConfigurationProperties
//@EnableTransactionManagement   // 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@MapperScan(basePackages = "com.dongl.boot_config_10_multi_datasource.dao.master",
sqlSessionTemplateRef = "masterSqlSessionTemplate")
public class MybatisMasterConfiguration {

    // master 数据库配置文件前缀
    final static String MASTER_PREFIX ="spring.datasource.master";
    // mapper.xml 文件存放路径
    public static final String MAPPER_XML_LOCATION = "classpath*:com/dongl/boot_config_10_multi_datasource/dao/master/mapper/*.xml";

    // 指定 master 数据库的 数据源配置
    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = MASTER_PREFIX)
    @Primary //优先方案，被注解的实现，优先被注入
    public DataSource masterDataSource(){
        return DataSourceBuilder.create().build();
    }


    // master 配置SQL SessionFactory 工厂,向工厂中注入数据源
    // @Qualifier：先声明后使用，相当于多个实现起多个不同的名字，注入时候告诉我你要注入哪个
    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) throws Exception{
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        // 配置 mapper.xml 的路径
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().
                getResources(MAPPER_XML_LOCATION));
        return sqlSessionFactory.getObject();
    }

    // 配置 SQL Session 的模板,注入SQL SessionFactory
    @Bean(name = "masterSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate masterSqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    // 配置事物
    @Bean(name = "masterTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager(@Qualifier("masterDataSource") DataSource dataSource ){
        return new DataSourceTransactionManager(dataSource);
    }

}

```
2.SpringBootSlave 数据库的数据源连接
```aidl
package com.dongl.boot_config_10_multi_datasource.config;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2020/1/12 16:16
 */
@Configuration
@EnableConfigurationProperties
@MapperScan(basePackages = "com.dongl.boot_config_10_multi_datasource.dao.slave",
        sqlSessionTemplateRef = "masterSqlSessionTemplate")
public class MybatisSlaveConfiguration {

    // slave 数据库配置文件前缀
    final static String SLAVE_PREFIX = "spring.datasource.slave";
    // mapper.xml 文件存放路径
    public static final String MAPPER_XML_LOCATION = "classpath*:com/dongl/boot_config_10_multi_datasource/dao/master/mapper/*.xml";

    @Bean(name = "slaveDataSource")
    @ConfigurationProperties(prefix = SLAVE_PREFIX)  //能够批量注入配置文件的属性,只需要指定一个前缀，就能绑定有这个前缀的所有属性值。
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "slaveSqlSessionFactory")
    @Primary
    public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("slaveDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_XML_LOCATION));
        return bean.getObject();
    }

    // 配置 SQL Session 的模板,注入SQL SessionFactory
    @Bean(name = "slaveSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate slaveSqlSessionTemplate(@Qualifier("slaveSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    // 配置事务管理器
    @Bean("slaveTransactionManager")
    @Primary
    public DataSourceTransactionManager slaveTransactionManager(@Qualifier("slaveDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}

```

注意：扫描 Dao 类和Mapper.xml的功能放在了 配置数据源的配置类中，
因此在启动类中 扫描 Dao 包的注解 就不需要了，
同时，在配置文件 application.properties 中 扫描 mapper.xml 的配置也不需要了。

```aidl

#配置mapper.xml 的位置 ，多数据源中已在各自的 MybatisMasterConfiguration 配置文件中配置，因此可以注释掉。
#mybatis.mapper-locations=classpath*:com.dongl.boot_config_10_multi_datasource.dao.mapper/*.xml

```
### 步骤3：配置pom.xml 中的  <resources>
```aidl
  <!-- 配置多数据源 为什么需要指定resources ？ -->
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.xml</include>
                </includes>
            </resource>
        </resources>
```

注：如果不配置<resources> 自己写的接口无法使用，但自动生成的接口可以使用。

### 步骤4：多数据源代码生成器 generator 配置
可以写多个 <context>  </context> 进行配置
```aidl
    <!-- 配置数据源Slave -->
    <!-- defaultModelType="flat" 设置复合主键时不单独为主键创建实体 -->
    <context id="MySqlSlave" targetRuntime="MyBatis3" defaultModelType="flat">
        <!-- 生成的Java文件的编码 -->
        <property name="javaFileEncoding" value="utf-8" />
        <!-- 格式化java代码 -->
        <property name="javaFormatter" value="org.mybatis.generator.api.dom.DefaultJavaFormatter"/>
        <!-- 格式化XML代码 -->
        <property name="xmlFormatter" value="org.mybatis.generator.api.dom.DefaultXmlFormatter"/>

        <!-- 生成的POJO实现java.io.Serializable接口 -->
        <plugin type="org.mybatis.generator.plugins.SerializablePlugin" />
        <!--添加自定义的继承接口-->
        <plugin type="tk.mybatis.mapper.generator.MapperPlugin">
            <property name="mappers" value="tk.mybatis.mapper.common.Mapper"/>
            <property name="caseSensitive" value="true"/>
        </plugin>
        <!--注释-->
        <commentGenerator>
            <!-- 将数据库中表的字段描述信息添加到注释 -->
            <property name="addRemarkComments" value="true"/>
            <!-- 注释里不添加日期 -->
            <property name="suppressDate" value="true"/>
        </commentGenerator>
        <!-- 数据库连接 -->
        <jdbcConnection
                driverClass="com.mysql.jdbc.Driver"
                connectionURL="jdbc:mysql://192.168.1.107:3306/SpringBootSlave"
                userId="root"
                password="123456"/>


        <!-- 非必需，类型处理器，在数据库类型和java类型之间的转换控制-->
        <!-- 默认false，把JDBC DECIMAL 和 NUMERIC 类型解析为 Integer，为 true时把JDBC DECIMAL 和
         NUMERIC 类型解析为java.math.BigDecimal -->
        <javaTypeResolver>
            <!-- 是否使用bigDecimal， false可自动转化以下类型（Long, Integer, Short, etc.） -->
            <property name="forceBigDecimals" value="false" />
        </javaTypeResolver>

        <!-- 生成POJO对象，并将类放到com.dongl.boot_config_10_multi_datasource.entity包下 -->
        <javaModelGenerator targetPackage="com.dongl.boot_config_10_multi_datasource.entity" targetProject="src/main/java">
            <!-- 从数据库返回的值被清理前后的空格 -->
            <property name="trimStrings" value="true" />
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="false" />
        </javaModelGenerator>

        <!-- 生成mapper xml文件，并放到resources下的mapper文件夹下 -->
        <sqlMapGenerator targetPackage="com.dongl.boot_config_10_multi_datasource.dao.slave.mapper" targetProject="src/main/java">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="false" />
        </sqlMapGenerator>

        <!-- 生成mapper xml对应dao接口，放到com.dongl.boot_config_10_multi_datasource.dao包下-->
        <javaClientGenerator targetPackage="com.dongl.boot_config_10_multi_datasource.dao.slave" targetProject="src/main/java" type="XMLMAPPER">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="false" />
        </javaClientGenerator>

        <!-- table标签可以有多个，至少一个，tableName指定表名，可以使用_和%通配符 -->
        <table tableName="red_packet_account" domainObjectName="redPacketAccount">
            <!-- 是否只生成POJO对象 -->
            <property name="modelOnly" value="false"/>
        </table>
    </context>
```

## 演示
```aidl
http://localhost:9090/name/Albert3
{
    "id":"38be0128e4f747eea0bd88c2ad37d0f7",
    "username":"Albert3",
    "password":"123456",
    "sex":0,
    "deleted":0,
    "createTime":"2020-01-11T07:39:18.000+0000",
    "updateTime":"2020-01-11T07:39:18.000+0000"
}
```