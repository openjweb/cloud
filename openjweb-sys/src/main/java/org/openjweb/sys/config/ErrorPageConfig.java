package org.openjweb.sys.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

//@Configuration
@Slf4j
public class ErrorPageConfig {

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {

                log.info("重新指定404页面................");
                ErrorPage err404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error-404.html");//不能直接命名404.html
                //ErrorPage err500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/error-500.html");//
                //ttpStatus.BAD_REQUEST:400,ttpStatus.INTERNAL_SERVER_ERROR 500
                factory.addErrorPages(err404Page);
                //factory.addErrorPages(err404Page,err500Page);
            }
        };
    }
}