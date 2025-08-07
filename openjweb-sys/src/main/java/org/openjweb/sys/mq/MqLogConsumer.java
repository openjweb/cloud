package org.openjweb.sys.mq;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * mq处理日志信息
 *
 * @author xd
 */
@Slf4j
//@Component
public class MqLogConsumer {

    @RabbitListener(queues = "LOG_QUEUE", containerFactory ="factory_single_pass_err")
    public void adminLogBindingConsumer(String msg) {
        try {
            log.info("接收日志消息::::::");
            log.info(msg);
        } catch (Exception e) {
            //修改拼团活动团队状态失败

        }
    }
}
