package org.openjweb.sys;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@ComponentScan(basePackages = {"org.openjweb"})
@MapperScan(value = {"org.openjweb.b2c.mapper","org.openjweb.core.mapper"})
@EnableScheduling
//@EnableWebSecurity
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableAuthorizationServer
public class OpenjwebSysApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenjwebSysApplication.class, args);
    }

}
