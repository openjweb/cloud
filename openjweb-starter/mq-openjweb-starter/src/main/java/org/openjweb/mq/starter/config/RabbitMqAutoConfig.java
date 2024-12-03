package org.openjweb.mq.starter.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(DefaultMqProperties.class)
public class RabbitMqAutoConfig {

    @Autowired DefaultMqProperties defaultMqProperties;

    private CachingConnectionFactory connectionFactory;

    /**
     * 连接工厂使用自定义配置
     *
     * @param connectionFactory  connectionFactory
     * @param defaultMqProperties starhumanMqProperties
     */
    public RabbitMqAutoConfig(CachingConnectionFactory connectionFactory,
                              DefaultMqProperties defaultMqProperties) {
        connectionFactory.setUsername(defaultMqProperties.getUsername());
        connectionFactory.setPassword(defaultMqProperties.getPassword());
        connectionFactory.setAddresses(defaultMqProperties.getHost());
        connectionFactory.afterPropertiesSet();
        this.connectionFactory = connectionFactory;
        System.setProperty("spring.amqp.deserialization.trust.all","true");
        //更新常量

    }



    /**
     * 交换机
     *
     * @return DirectExchange
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(  defaultMqProperties.getNamePrefix()+"_exchange", true, false);
    }

    /**
     * 延迟队列交换机
     *
     * @return DirectExchange
     */
    @Bean
    public DirectExchange lazyExchange() {
        DirectExchange directExchange = new DirectExchange(defaultMqProperties.getNamePrefix()+"_lazy_exchange",  true, false);
        directExchange.setDelayed(true);
        return directExchange;
    }

    /**
     * admin操作日志队列
     *
     * @return Queue
     */
    @Bean
    public Queue adminLogQueue() {
        return new Queue("LOG_QUEUE", true);
    }

    @Bean
    public Binding adminLogBinding() {
        return BindingBuilder.bind(adminLogQueue()).to(directExchange()).with("LOG_QUEUE");
    }
    /**
     * 连接工厂，单一消费者，发生异常丢弃消息
     */
    @Bean("factory_single_pass_err")
    public SimpleRabbitListenerContainerFactory starhumanMqFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        factory.setBatchSize(1);
        //跳过异常
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        return factory;
    }

}
