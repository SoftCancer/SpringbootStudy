package com.dongl.boot_config_13_redis.config.datasourceconfig;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2020/1/12 15:10
 */

@Configuration
//@EnableConfigurationProperties
//@EnableTransactionManagement // 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@MapperScan(basePackages = "com.dongl.boot_config_13_redis.dao.master",
sqlSessionTemplateRef = "masterSqlSessionTemplate")
public class MybatisMasterConfiguration {

    // master 数据库配置文件前缀
//    final static String MASTER_PREFIX ="spring.datasource.druid.master";
    // mapper.xml 文件存放路径
    public static final String MAPPER_XML_LOCATION = "classpath*:com/dongl/boot_config_13_redis/dao/master/mapper/*.xml";

    // 指定 master 数据库的 数据源配置
//    @Bean(name = "masterDataSource")
//    @ConfigurationProperties(prefix = MASTER_PREFIX)
//    @Primary //优先方案，被注解的实现，优先被注入
//    public DataSource masterDataSource(){
//        return DataSourceBuilder.create().build();
//        return new DruidDataSource();
//    }


    @Bean(name = "masterDataSource")
    @Primary
    public DataSource masterDataSource(MasterConfig masterConfig) throws Exception{
//        使用Druid的分布式驱动，暂时发现不支持MySql8以上的版本
//        DruidXADataSource druidXADataSource = new DruidXADataSource();
//        BeanUtils.copyProperties(masterConfig, druidXADataSource);

        //使用mysql的分布式驱动，支持MySql5.*、MySql8.* 以上版本
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl(masterConfig.getUrl());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXADataSource.setPassword(masterConfig.getPassword());
        mysqlXADataSource.setUser(masterConfig.getUsername());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);

        // 将本地事务注册到创 Atomikos全局事务
        AtomikosDataSourceBean adsBean = new AtomikosDataSourceBean();
        adsBean.setXaDataSource(mysqlXADataSource);
        adsBean.setUniqueResourceName("masterDataSource");

        adsBean.setMinPoolSize(masterConfig.getMinPoolSize());
        adsBean.setMaxPoolSize(masterConfig.getMaxPoolSize());
        adsBean.setMaxLifetime(masterConfig.getMaxLifetime());
        adsBean.setBorrowConnectionTimeout(masterConfig.getBorrowConnectionTimeout());
        adsBean.setLoginTimeout(masterConfig.getLoginTimeout());
        adsBean.setMaintenanceInterval(masterConfig.getMaintenanceInterval());
        adsBean.setMaxIdleTime(masterConfig.getMaxIdleTime());
        adsBean.setTestQuery(masterConfig.getTestQuery());
        return adsBean;
    }

    // master 配置SQL SessionFactory 工厂,向工厂中注入数据源
    // @Qualifier：先声明后使用，相当于多个实现起多个不同的名字，注入时候告诉我你要注入哪个
    @Bean(name = "masterSqlSessionFactory")
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
    public SqlSessionTemplate masterSqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    // 配置事物
//    @Bean(name = "masterTransactionManager")
//    @Primary
//    public DataSourceTransactionManager masterTransactionManager(@Qualifier("masterDataSource") DataSource dataSource ){
//        return new DataSourceTransactionManager(dataSource);
//    }

}
