package org.openjweb.sys.config;

import cn.hutool.crypto.symmetric.AES;
import lombok.RequiredArgsConstructor;
import org.openjweb.core.service.CommUserService;
import org.openjweb.sys.auth.security.AESPasswordEncoder;
import org.openjweb.sys.auth.security.MD5PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



    private static final String[] ALLOW_URL_LIST = {
            //
            "/login",
            "/logout",
            "/captcha",
            "/favicon.ico",
            //"/api/jwt/**",
            "/api/cms/**",
            "/api/b2c/**",
            "/api/b2b2c/**",
            "/api/sns/**",
            "/api/comm/**",
            "/api/cms1/**",
            "/api/store/**",
            "/demo/**"

    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                //登录表单
                .formLogin()
                .and()
                .authorizeRequests()
                .antMatchers(ALLOW_URL_LIST).permitAll()
                .anyRequest().authenticated();
    }


    /*@Bean
    PasswordEncoder PasswordEncoder() {
        //return md5PasswordEncoder;
        //return aesPasswordEncoder;//这个不行
        return new AESPasswordEncoder();
        //return new BCryptPasswordEncoder();
        //return new Md5PasswordEncoder();

    }*/
    @Autowired
    MD5PasswordEncoder md5PasswordEncoder;

    @Autowired
    AESPasswordEncoder aesPasswordEncoder;


    @Autowired
    CommUserService userDetailService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailService);
        auth.userDetailsService(userDetailService).passwordEncoder(aesPasswordEncoder);
        //auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());

    }

}
