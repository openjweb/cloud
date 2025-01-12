package org.openjweb.core.config;

import lombok.extern.slf4j.Slf4j;
import org.beetl.core.GroupTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${beetl.fileTemplatesPath:}") String fileTemplatesPath;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/testduoyu").setViewName("testduoyu");//不能命名testlocale 可能locale有冲突
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("fileTemplatePath::");
        log.info(fileTemplatesPath);
        if(!fileTemplatesPath.endsWith("/"))fileTemplatesPath+="/";
        //D:/tmp/beetl/templates
        registry.addResourceHandler("/**")
                .addResourceLocations("file:"+fileTemplatesPath)
                .addResourceLocations("classpath:/static/");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("设置国际化参数lang...........");
        //默认拦截器 其中lang表示切换语言的参数名 LocaleChangeInterceptor 指定切换国际化语言的参数名。
        // 例如?lang=zh_CN 表示读取国际化文件messages_zh_CN.properties。
        //System.out.println("增加国际化拦截器...........");
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");// 指定设置国际化的参数
        registry.addInterceptor(localeInterceptor);

    }
}
