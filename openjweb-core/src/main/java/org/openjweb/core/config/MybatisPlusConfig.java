package org.openjweb.core.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.Oracle12cDialect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@Slf4j
public class MybatisPlusConfig {
    @Autowired
    private DataSource ds1;

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();//如果配置多个插件,切记分页最后添加
        //增加乐观锁拦截器，对应实体类的乐观锁字段可加@Version注解
        log.info("开始增加乐观锁拦截器......................");
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        DbType dbType = getDbType(ds1);


        //建议单一数据库类型的均设置 dbType
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor(dbType);
        // 自定义 Oracle 分页 SQL 生成器
        boolean isOracle = false;
        try{
            isOracle =  ds1.getConnection().getMetaData().getURL().toLowerCase().indexOf(":oracle:")>-1;
        }
        catch (Exception ex){}

        if(isOracle) {
            log.info("是ORACLE数据库，自定义ORACLE DIALECT。。。。。。。。");
            paginationInterceptor.setDialect(new CustomOracleDialect()); // 使用自定义 Dialect
        }
        else{
            log.info("非ORACLE数据库。。。。。。。。。。。");
        }
       // paginationInterceptor.setOptimizeJoin(false); // 避免 JOIN 优化导致列名冲突
        interceptor.addInnerInterceptor(paginationInterceptor);//如果有多数据源可以不配具体类型 否则都建议配上具体的DbType

        return interceptor;
    }

    private DbType getDbType(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            String url = connection.getMetaData().getURL();

            if (url.contains(":mysql:")) {
                log.info("DB iSMYSQL");
                return DbType.MYSQL;
            } else if (url.contains(":oracle:")) {
                log.info("DB ORACLE");
                return DbType.ORACLE;
            } else if (url.contains(":postgresql:")) {
                return DbType.POSTGRE_SQL;
            } else if (url.contains(":sqlserver:")) {
                return DbType.SQL_SERVER;
            } else if (url.contains(":h2:")) {
                return DbType.H2;
            } else {
                log.info("DB is mysql...............");
                // 默认使用MySQL
                return DbType.MYSQL;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to determine database type", e);
        }
    }
}