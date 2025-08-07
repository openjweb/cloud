package org.openjweb.sys.job;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.openjweb.common.util.StringUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Configuration
@EnableScheduling   //2.开启定时任务
public class LogJob {

    @Scheduled(cron = "0 0/5 * * * ?")
    @SchedulerLock(name = "jobWriteLog", lockAtMostFor = "60000", lockAtLeastFor = "50000")
    public void jobWriteLog()   {
        log.info(StringUtil.getCurrentDateTime()+"开始向数据库中写入日志。。。。。。。。。。。");


    }
}
