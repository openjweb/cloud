package org.openjweb.sys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.retry.annotation.EnableRetry;

import javax.annotation.Resource;


@SpringBootApplication
//@EnableRetry
@ComponentScan(basePackages = {"org.openjweb"})
@MapperScan(value = {"org.openjweb.b2c.mapper","org.openjweb.core.mapper"})
public class OpenjwebSysApplication {
   // @Resource
    //private RedisTemplate<String, Object> objectRedisTemplate;
    //@Resource
    //private StringRedisTemplate stringRedisTemplate;
    public static void main(String[] args) {
        SpringApplication.run(OpenjwebSysApplication.class, args);
    }

}
