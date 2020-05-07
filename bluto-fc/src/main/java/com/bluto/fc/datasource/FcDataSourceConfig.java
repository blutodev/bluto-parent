package com.bluto.fc.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * fc data source
 * Created by bluto on 2020/5/7.
 */
@Configuration
@MapperScan(basePackages = FcDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "fcSqlSessionFactory")
public class FcDataSourceConfig {

    static final String PACKAGE = "com.bluto.fc.dao";
    static final String MAPPER_LOCATION = "classpath:com/bluto/fc/mapper/**/*.xml";

    @Value("${bluto.rent.datasource.driverClassName}")
    private String driverClass;

    @Value("${bluto.rent.datasource.url}")
    private String url;

    @Value("${bluto.rent.datasource.username}")
    private String username;

    @Value("${bluto.rent.datasource.password}")
    private String password;

    @Value("${bluto.rent.druid.initialSize}")
    private int initialSize;

    @Value("${bluto.rent.druid.minIdle}")
    private int minIdle;

    @Value("${bluto.rent.druid.maxActive}")
    private int maxActive;

    @Value("${bluto.rent.druid.maxActive}")
    private int maxWait;

    @Value("${bluto.rent.druid.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${bluto.rent.druid.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${bluto.rent.druid.validationQuery}")
    private String validationQuery;

    @Value("${bluto.rent.druid.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${bluto.rent.druid.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${bluto.rent.druid.testOnReturn}")
    private boolean testOnReturn;

    @Value("${bluto.rent.druid.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${bluto.rent.druid.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${bluto.rent.druid.filters}")
    private String filters;

    @Value("${bluto.rent.druid.connectionProperties}")
    private String connectionProperties;

    @Bean(name = "fcDataSource")
    public DataSource fcDataSource() {
        DruidDataSource fcDataSource = new DruidDataSource();
        fcDataSource.setDriverClassName(driverClass);
        fcDataSource.setUrl(url);
        fcDataSource.setUsername(username);
        fcDataSource.setPassword(password);
        fcDataSource.setInitialSize(initialSize);
        fcDataSource.setMinIdle(minIdle);
        fcDataSource.setMaxActive(maxActive);
        fcDataSource.setMaxWait(maxWait);
        fcDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        fcDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        fcDataSource.setValidationQuery(validationQuery);
        fcDataSource.setTestWhileIdle(testWhileIdle);
        fcDataSource.setTestOnBorrow(testOnBorrow);
        fcDataSource.setTestOnReturn(testOnReturn);
        fcDataSource.setPoolPreparedStatements(poolPreparedStatements);
        fcDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            fcDataSource.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fcDataSource;
    }

    // 配置事物管理
    @Bean(name = "fcTransactionManager")
    @Primary
    public DataSourceTransactionManager fcTransactionManager() {
        return new DataSourceTransactionManager(fcDataSource());
    }

    @Bean(name = "fcSqlSessionFactory")
    @Primary
    public SqlSessionFactory fcSqlSessionFactory(@Qualifier("fcDataSource") DataSource fcDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(fcDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(FcDataSourceConfig.MAPPER_LOCATION));

        //分页插件
//        PageInterceptor pageHelper = new PageInterceptor();
//        Properties properties = new Properties();
//        properties.setProperty("helperDialect", "sqlserver");
//        properties.setProperty("reasonable", "true");
//        properties.setProperty("supportMethodsArguments", "true");
//        properties.setProperty("returnPageInfo", "check");
//        properties.setProperty("params", "count=countSql;pageNum=pageNumKey;pageSize=pageSizeKey;");
//        pageHelper.setProperties(properties);
//        sessionFactory.setPlugins(new Interceptor[]{pageHelper});

        return sessionFactory.getObject();
    }


}
