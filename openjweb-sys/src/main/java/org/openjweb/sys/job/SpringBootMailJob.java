package org.openjweb.sys.job;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.openjweb.core.mail.MailService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;

@Slf4j
@Component
@Configuration
@EnableScheduling   //2.开启定时任务
public class SpringBootMailJob {

    @Resource
    private MailService mailService;

    private String toMail = "yyy@qq.com";

    //@Scheduled(cron = "0 0/2 * * * ?")
    //@SchedulerLock(name = "jobSendMail", lockAtMostFor = "60000", lockAtLeastFor = "50000")
    public void doSendMail()   {
        //本例测试成功
        log.info("使用Springboot的mailSender.发送邮件,带图片附件.......");

        try {
            mailService.sendMailTo(toMail,"mailsender2","我在测试","niu.png","/root/niu.png");
            log.info("使用Springboot的mailSender.发送邮件完毕.......");
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //@Scheduled(cron = "0 0/2 * * * ?")
    //@SchedulerLock(name = "jobPicMail", lockAtMostFor = "60000", lockAtLeastFor = "50000")
    public void doSendPicMail()   {
       log.info("开始发正文带图片邮件......");
        //在邮件正文中用cid引用发送的图片资源,注意收到邮件后，有的邮件服务器默认不显示正文的图片，需要点显示图片
        String[] resIds = new String[] {"pic1","pic2"};
        String[] files = new String[] {"/root/niu.jpg","/root/niu2.jpg"};
        String content = "<div> hello,测试带图片邮件:图片1:<div><img src='cid:pic1'/></div>"
                +"图片2:<div><img src='cid:pic2'/></div></div>";
        try {
            mailService.sendMailWithImg ("baozhengw@163.com",toMail,"测试带图片邮件",
                    content, files,resIds);
            log.info("开始发正文带图片邮件完毕......");
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    //@Scheduled(cron = "0 0/2 * * * ?")
    //@SchedulerLock(name = "jobSendFreemarkerEmail", lockAtMostFor = "60000", lockAtLeastFor = "50000")
    public void doSendFreemarkerEmail() {
        log.info("开始发freemarker模板邮件......");//测试通过
        try {
            try {
                mailService.sendFreemarkerMail("baozhengw@163.com",toMail,"测试freemarker模板邮件" );
            } catch (MessagingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (IOException | TemplateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    //@Scheduled(cron = "0 0/2 * * * ?")
    //@SchedulerLock(name = "jobSendThymeleafEmail", lockAtMostFor = "60000", lockAtLeastFor = "50000")
    public void doSendThymeleafEmail() {
        log.info("开始发thymeleaf模板邮件......");
        //在邮件正文中用cid引用发送的图片资源
        try {
            mailService.sendMailThymeLeaf("baozhengw@163.com", toMail, "测试thymeleaf模板邮件");
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}
