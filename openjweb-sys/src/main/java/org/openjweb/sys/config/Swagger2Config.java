package org.openjweb.sys.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
//@EnableSwagger2
@EnableSwagger2WebMvc
//@EnableKnife4j
public class Swagger2Config {
    @Bean(value = "dockerBean")
    public Docket dockerBean() {
        //指定使用Swagger2规范
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder().title("OpenJWeb低代码平台开发文档")

                        //描述字段支持Markdown语法
                        .description("# OpenJWeb低代码开发平台WXID: openjweb") //产品简介
                        .termsOfServiceUrl("https://github.com/openjweb/cloud/tree/master")//API服务条款
                        .contact("29803446@qq..com")//联系人
                        .version("1.0")//版本号
                        .build())
                //分组名称
                .groupName("OpenJWeb低代码平台")//左上角搜索框---分组名称
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("org.openjweb"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
