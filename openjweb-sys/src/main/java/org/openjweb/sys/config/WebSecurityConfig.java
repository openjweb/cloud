package org.openjweb.sys.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

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
            "/api/store/**"

    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                // 登录配置

                .formLogin()

                .and()

                .authorizeRequests()

                .antMatchers(ALLOW_URL_LIST).permitAll()
                .anyRequest().authenticated()

        ;
    }
}
