package org.openjweb.core.config;

import lombok.extern.slf4j.Slf4j;
import org.beetl.core.GroupTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
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

    @Override
    public void addCorsMappings(CorsRegistry registry) { //为了vue前端proxy增加跨域设置
        log.info("设置跨域允许的请求.................");
        //When allowCredentials is true, allowedOrigins cannot contain the special value "*" since that cannot be set on the "Access-Control-Allow-Origin" response header. To allow credentials to a set of origins, list them explicitly or consider using "allowedOriginPatterns" instead.

        registry.addMapping("/**") // 对所有路径生效
                .allowedOrigins(new String[]{"http://localhost:81","http://c0001-1.zzyicheng.cn","http://localhost"}) // 允许的源---本地开发环境
                //.allowedOrigins(new String[]{"http://localhost:81","http://c0001-1.zzyicheng.cn"}) // 允许的源---本地开发环境

                //.allowedOrigins("*") // 允许的源，服务器正式环境，需要确定具体应该设置什么
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的方法
                .allowedHeaders("*") // 允许的头部
                .allowCredentials(true) // 是否发送Cookie
                .maxAge(3600); // 预检请求的缓存时间
    }
}
