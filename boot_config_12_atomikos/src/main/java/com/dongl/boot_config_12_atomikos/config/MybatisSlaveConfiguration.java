package com.dongl.boot_config_12_atomikos.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
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
 * @date: 2020/1/12 16:16
 */
@Configuration
//@EnableTransactionManagement
//@EnableConfigurationProperties
@MapperScan(basePackages = "com.dongl.boot_config_12_atomikos.dao.slave",
        sqlSessionTemplateRef = "slaveSqlSessionTemplate")
public class MybatisSlaveConfiguration {

    // slave 数据库配置文件前缀
//    final static String SLAVE_PREFIX = "spring.datasource.druid.slave";
    // mapper.xml 文件存放路径
    public static final String MAPPER_XML_LOCATION = "classpath*:com/dongl/boot_config_12_atomikos/dao/slave/mapper/*.xml";

//    @Bean(name = "slaveDataSource")
//    @ConfigurationProperties(prefix = SLAVE_PREFIX)  //能够批量注入配置文件的属性,只需要指定一个前缀，就能绑定有这个前缀的所有属性值。
//    public DataSource slaveDataSource() {
//        return DataSourceBuilder.create().build();
//        return new DruidDataSource();
//    }

    @Bean(name = "slaveDataSource")
    public DataSource slaveDataSource(SlaveConfig slaveConfig) throws Exception{
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl(slaveConfig.getUrl());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXADataSource.setPassword(slaveConfig.getPassword());
        mysqlXADataSource.setUser(slaveConfig.getUsername());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean adsBean = new AtomikosDataSourceBean();
        adsBean.setXaDataSource(mysqlXADataSource);
        adsBean.setUniqueResourceName("slaveDataSource");
        adsBean.setMinPoolSize(slaveConfig.getMinPoolSize());
        adsBean.setMaxPoolSize(slaveConfig.getMaxPoolSize());
        adsBean.setMaxLifetime(slaveConfig.getMaxLifetime());
        adsBean.setBorrowConnectionTimeout(slaveConfig.getBorrowConnectionTimeout());
        adsBean.setLoginTimeout(slaveConfig.getLoginTimeout());
        adsBean.setMaintenanceInterval(slaveConfig.getMaintenanceInterval());
        adsBean.setMaxIdleTime(slaveConfig.getMaxIdleTime());
        adsBean.setTestQuery(slaveConfig.getTestQuery());
        return adsBean;
    }

    @Bean(name = "slaveSqlSessionFactory")
    public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("slaveDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_XML_LOCATION));
        return bean.getObject();
    }

    // 配置 SQL Session 的模板,注入SQL SessionFactory
    @Bean(name = "slaveSqlSessionTemplate")
//    @Primary
    public SqlSessionTemplate slaveSqlSessionTemplate(@Qualifier("slaveSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    // 配置事务管理器
//    @Bean("slaveTransactionManager")
//    public DataSourceTransactionManager slaveTransactionManager(@Qualifier("slaveDataSource") DataSource dataSource){
//        return new DataSourceTransactionManager(dataSource);
//    }


}
