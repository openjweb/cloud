package org.openjweb.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.openjweb"})

public class OpenjwebCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenjwebCmsApplication.class, args);
    }

}
