package org.openjweb.sys.api;

import lombok.extern.slf4j.Slf4j;
import org.openjweb.mq.starter.config.DefaultMqProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * http://localhost:8001/demo/api/mq/test
 */
@Slf4j
@RequestMapping("/demo/api/mq")
@RestController
public class RabbitMQDemoApi {



    @Autowired
    DefaultMqProperties defaultMqProperties;
    @RequestMapping("test")
    public String test(){
        String host = defaultMqProperties.getHost();//读取成功
        return host;

    }

    @Resource
    private RabbitTemplate rabbitTemplate;

    //http://localhost:8001/demo/api/mq/newMsg
    //http://localhost:15672/#/

    @RequestMapping("newMsg")
    public String newMsg(){
        rabbitTemplate.convertAndSend( "openjweb-cloud_exchange", "LOG_QUEUE", "hello,i'm msg");
        //String host = defaultMqProperties.getHost();//读取成功
        return "success";

    }
}
