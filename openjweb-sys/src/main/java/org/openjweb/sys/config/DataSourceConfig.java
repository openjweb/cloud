package org.openjweb.sys.config;
import javax.sql.DataSource;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@Slf4j
@Configuration
public class DataSourceConfig {
    @Primary
    @Bean(name = "dsOne")
    @ConfigurationProperties("spring.datasource.ds1")
    DataSource dsOne() {
        DruidDataSource ds1 = new DruidDataSource();
        /*ds1.setUrl("jdbc:mysql://localhost:3306/erp");
        ds1.setUsername("root");
        ds1.setPassword("root");*/
        return ds1;
    }

    //@Bean
    @Bean(name = "dsTwo")
    @ConfigurationProperties("spring.datasource.ds2")
    DataSource dsTwo() {
        DruidDataSource ds2 = new DruidDataSource();
        /*ds2.setUrl("jdbc:mysql://localhost:3306/erp");
        ds2.setUsername("root");
        ds2.setPassword("root");*/
        return ds2;
    }

    //为第一个数据源绑定一个事务管理器
    @Bean(name = "transactionManager1")
    public DataSourceTransactionManager dataSourceTransactionManagerOne(@Qualifier("dsOne") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "transactionManager2")
    public DataSourceTransactionManager dataSourceTransactionManagerTwo(@Qualifier("dsTwo") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
