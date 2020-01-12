package com.dongl.boot_config_10_multi_datasource.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

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
