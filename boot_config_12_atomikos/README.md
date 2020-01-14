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


### 步骤2： 配置application.properties 文件
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

### 步骤3：新建配置类文件

新建 MasterConfig.java 和 SlaveConfig.java 类 ，内容如下：
```aidl

@Data
// 注意这个前缀要和application.properties文件的前缀一样
@ConfigurationProperties(prefix = "spring.datasource.druid.master")
public class MasterConfig {
    private String url;
    // 比如这个url在properties中是这样子的spring.datasource.druid.master.url
    private String username;
    private String password;
    private int minPoolSize;
    private int maxPoolSize;
    private int maxLifetime;
    private int borrowConnectionTimeout;
    private int loginTimeout;
    private int maintenanceInterval;
    private int maxIdleTime;
    private String testQuery;
}

```

### 步骤4： 修改 MybatisMasterConfiguration.java 和 MybatisSlaveConfiguration.java 数据源配置类

修改如下：
```aidl

@Configuration
//@EnableConfigurationProperties
//@EnableTransactionManagement // 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@MapperScan(basePackages = "com.dongl.boot_config_12_atomikos.dao.master",
sqlSessionTemplateRef = "masterSqlSessionTemplate")
public class MybatisMasterConfiguration {

    // master 数据库配置文件前缀
//    final static String MASTER_PREFIX ="spring.datasource.druid.master";
    // mapper.xml 文件存放路径
    public static final String MAPPER_XML_LOCATION = "classpath*:com/dongl/boot_config_12_atomikos/dao/master/mapper/*.xml";

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

```
### 步骤5： 在Service 的相应方法上添加注释
```aidl

    @Transactional(rollbackFor = Exception.class)
    public void payAccout(String userId,int account){
        CapitalAccount capitalAccount = new CapitalAccount();
        capitalAccount.setUserId(userId);
        CapitalAccount oldCapitalAccount = accountMapper.selectOne(capitalAccount);
        oldCapitalAccount.setBalanceAmount(oldCapitalAccount.getBalanceAmount() -account);
        accountMapper.updateByPrimaryKey(oldCapitalAccount);
    }

```

### 步骤6：测试
```aidl
http://localhost:9090/pay

代码异常，数据无操作

```

## 新增声明式事务TransactionConfig.java，暂没使用
