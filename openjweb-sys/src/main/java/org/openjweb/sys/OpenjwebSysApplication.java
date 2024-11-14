package org.openjweb.sys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;


@SpringBootApplication
@ComponentScan(basePackages = {"org.openjweb"})
public class OpenjwebSysApplication {
   // @Resource
    //private RedisTemplate<String, Object> objectRedisTemplate;
    //@Resource
    //private StringRedisTemplate stringRedisTemplate;
    public static void main(String[] args) {
        SpringApplication.run(OpenjwebSysApplication.class, args);
    }

}
