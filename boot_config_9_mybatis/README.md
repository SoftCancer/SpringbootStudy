## 一、创建多模块项目

## 二、修改端口号
默认端口：8080，修改端口：9090
server.port=9090

修改为随机端口

server.port=${random.int(2048,99999)}


## SpringBoot 集成 Mybatis Generator （代码生成器）
###步骤1：创建表
```aidl
    CREATE TABLE `NewTable` (
    `id`  varchar(32) NOT NULL ,
    `username`  varchar(20) NULL ,
    `password`  varchar(20) NULL ,
    `sex`  tinyint(4) NULL DEFAULT 0 COMMENT '性别：女：0，男：1' ,
    `deleted`  tinyint(4) NULL DEFAULT 0 COMMENT '删除标识：0：不删除，1：删除' ,
    `create_time`  timestamp NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间' ,
    `update_time`  timestamp NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间' ,
    PRIMARY KEY (`id`)
    )
    COMMENT='用户表'
    ;
```

### 步骤2：在pom.xml 中添加依赖包
1. 在pom.xml中的maven 构件中添加

```aidl
  <!-- mybatis的generator -->
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.7</version>
                <configuration>
                    <configurationFile>${basedir}/src/main/resources/generator/generatorConfig.xml</configurationFile>
                    <overwrite>true</overwrite>
                    <verbose>true</verbose>
                </configuration>

                <dependencies>
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>${mysql_connector.version}</version>
                    </dependency>
                    <dependency>
                        <groupId>tk.mybatis</groupId>
                        <artifactId>mapper</artifactId>
                        <version>${mapper.version}</version>
                    </dependency>
                </dependencies>
            </plugin>
```

2.在pom.xml 中加入依赖
```
 <!-- 集成mybatis的generator -->
        <dependency>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-core</artifactId>
            <version>1.3.5</version>
        </dependency>

        <!-- 通用 mapper -->
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper</artifactId>
            <version>${mapper.version}</version>
        </dependency>
```

### 步骤3: 两个核心配置文件
1. 配置 application.properties
```aidl

## 基础生成的包名
package.name=com.dongl.boot_config_9_mybatis
## 数据源配置
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
#spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
spring.datasource.url=jdbc:mysql://192.168.1.107:3306/SpringbBootStudy?useUnicode=true&characterEncoding=utf-8&useSSL=false
spring.datasource.username=root
spring.datasource.password=123456
```

2. 新建配置 generatorConfig.xml文件
```aidl
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
    <!-- 引入配置文件 -->
    <properties resource="application.properties"/>
    <!-- 引入配置文件 -->
    <!-- defaultModelType="flat" 设置复合主键时不单独为主键创建实体 -->
    <context id="MySql" targetRuntime="MyBatis3" defaultModelType="flat">
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
                connectionURL="jdbc:mysql://192.168.1.107:3306/SpringBootStudy"
                userId="root"
                password="123456"/>


        <!-- 非必需，类型处理器，在数据库类型和java类型之间的转换控制-->
        <!-- 默认false，把JDBC DECIMAL 和 NUMERIC 类型解析为 Integer，为 true时把JDBC DECIMAL 和
         NUMERIC 类型解析为java.math.BigDecimal -->
        <javaTypeResolver>
            <!-- 是否使用bigDecimal， false可自动转化以下类型（Long, Integer, Short, etc.） -->
            <property name="forceBigDecimals" value="false" />
        </javaTypeResolver>

        <!-- 生成POJO对象，并将类放到com.dongl.boot_config_9_mybatis.entity包下 -->
        <javaModelGenerator targetPackage="com.dongl.boot_config_9_mybatis.entity" targetProject="src/main/java">
            <!-- 从数据库返回的值被清理前后的空格 -->
            <property name="trimStrings" value="true" />
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="false" />
        </javaModelGenerator>

        <!-- 生成mapper xml文件，并放到resources下的mapper文件夹下 -->
        <sqlMapGenerator targetPackage="com.dongl.boot_config_9_mybatis.dao.mapper"  targetProject="src/main/java">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="false" />
        </sqlMapGenerator>

        <!-- 生成mapper xml对应dao接口，放到com.dongl.boot_config_9_mybatis.dao包下-->
        <javaClientGenerator targetPackage="com.dongl.boot_config_9_mybatis.dao" targetProject="src/main/java" type="XMLMAPPER">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="false" />
        </javaClientGenerator>

        <!-- table标签可以有多个，至少一个，tableName指定表名，可以使用_和%通配符 -->

        <table tableName="user" domainObjectName="User">
            <!-- 是否只生成POJO对象 -->
            <property name="modelOnly" value="false"/>
            <!-- 在有自增ID的时，需要此配置 -->
            <!--<generatedKey column="id" sqlStatement="JDBC"/>-->
            <!--<columnRenamingRule searchString="^flower_" replaceString="" />-->
            <!-- 数据库中表名有时我们都会带个前缀，而实体又不想带前缀，这个配置可以把实体的前缀去掉 -->
            <!--<domainObjectRenamingRule searchString="^Cyber" replaceString="" />-->

        </table>

    </context>
</generatorConfiguration>

```


## SpringBoot 集成Mybatis 

###步骤1：在pom.xml 中添加 jar包

```aidl
        <!-- alibaba的druid数据库连接池 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.10</version>
        </dependency>

        <!-- SpringBoot 集成Mybatis -->
        <!--mysql jdbc驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.46</version>
        </dependency>
```

### 步骤3：在 application.properties 中配置mapper的扫描路径
```aidl
#配置mapper.xml 的位置
mybatis.mapper-locations=classpath*:com.dongl.boot_config_9_mybatis.dao.mapper/*.xml
# 显示sql日志
logging.level.com.dongl.boot_config_9_mybatis=debug

```
### 步骤4：在启动类中配置扫描dao 类的路径
```aidl

@MapperScan("com.dongl.boot_config_9_mybatis.dao")
@SpringBootApplication
public class BootMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootMybatisApplication.class, args);
    }
}

```





