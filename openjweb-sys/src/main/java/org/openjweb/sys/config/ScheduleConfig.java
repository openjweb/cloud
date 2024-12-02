package org.openjweb.sys.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

/**
 * @Author wbz
 */
@Configuration
public class ScheduleConfig   implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //设定一个长度30的定时任务线程池
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(30));
    }
}
