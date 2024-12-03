package org.openjweb.mq.starter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "mq.rabbit")
@Data
@Component
public class DefaultMqProperties {
    /**
     * rabbitMq 名称前缀（交换机、队列名称）
     */
    private String namePrefix ;

    /**
     * mq host
     */
    private String host  ;

    /**
     * mq 用户名
     */
    private String username  ;

    /**
     * mq 密码
     */
    private String password ;
}
