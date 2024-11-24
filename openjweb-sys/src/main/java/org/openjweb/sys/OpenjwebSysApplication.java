package org.openjweb.sys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.openjweb"})
@MapperScan(value = {"org.openjweb.b2c.mapper","org.openjweb.core.mapper"})
public class OpenjwebSysApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenjwebSysApplication.class, args);
    }

}
