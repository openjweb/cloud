package org.openjweb.core.config;

import java.util.Locale;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
//配置国际化语言
@Configuration
@Slf4j
public class LocaleConfig {
    /**
     *  默认解析器 其中locale表示默认语言 LocaleResolver 用于设置当前会话的默认的国际化语言。
     */
    @Bean
    public LocaleResolver localeResolver() {
        log.info("LocaleConfig设置国际化默认语种...........");
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.CHINA);//指定默认语言
        return localeResolver;
    }

}
