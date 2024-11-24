package org.openjweb.core.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
@Slf4j
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();//如果配置多个插件,切记分页最后添加
        //增加乐观锁拦截器，对应实体类的乐观锁字段可加@Version注解
        log.info("开始增加乐观锁拦截器......................");
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

        //建议单一数据库类型的均设置 dbType
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));//如果有多数据源可以不配具体类型 否则都建议配上具体的DbType

        return interceptor;
    }
}