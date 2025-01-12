package org.openjweb.core.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.FileResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.openjweb.core.service.BeetlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;

@Configuration
@Slf4j
public class BeetlConf {

    @Autowired
    private WebApplicationContext wac;

    //@Value("${beetl.templatesPath}") String templatesPath;//模板根目录 ，比如 "templates"
    String templatesPath = "templates";//这个给类定义使用的
    @Value("${beetl.fileTemplatesPath:}") String fileTemplatesPath;//模板根目录 ，比如 "templates"
    String templateFilePath = "";
    @Bean(name = "beetlConfig")
    public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
        BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
        //获取Spring Boot 的ClassLoader
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        if(loader==null){
            loader = BeetlConf.class.getClassLoader();
        }
        //log.info("当前指定的Beetl模板文件路径："+String.valueOf(fileTemplatesPath));
        //beetlGroupUtilConfiguration.setConfigProperties(extProperties);//额外的配置，可以覆盖默认配置，一般不需要

        if(StringUtils.isNotEmpty(fileTemplatesPath)){
            log.info("使用文件模版路径...........");
            FileResourceLoader floader = new FileResourceLoader(fileTemplatesPath);
            beetlGroupUtilConfiguration.setResourceLoader(floader);
        }
        else{
            log.info("使用类模版路径...........");
            ClasspathResourceLoader cploder = new ClasspathResourceLoader(loader,  templatesPath);
            beetlGroupUtilConfiguration.setResourceLoader(cploder);
        }

        beetlGroupUtilConfiguration.init();


        //如果使用了优化编译器，涉及到字节码操作，需要添加ClassLoader
        beetlGroupUtilConfiguration.getGroupTemplate().setClassLoader(loader);

        //注册国际化函数
        beetlGroupUtilConfiguration.getGroupTemplate().registerFunction("i18n", new I18nFunction(wac));
        //注册cms相关的方法
        beetlGroupUtilConfiguration.getGroupTemplate().registerFunction("cmsUtil",new BeetlService());

        return beetlGroupUtilConfiguration;

    }

    @Bean(name = "beetlViewResolver")
    public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
        BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
        beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
        beetlSpringViewResolver.setOrder(0);
        beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
        return beetlSpringViewResolver;
    }


}