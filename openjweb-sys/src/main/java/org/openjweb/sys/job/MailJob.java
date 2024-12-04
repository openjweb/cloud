package org.openjweb.sys.job;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.openjweb.common.util.StringUtil;
import org.openjweb.core.mail.Mail;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Configuration
@EnableScheduling   //2.开启定时任务
public class MailJob {
    //@Scheduled(cron = "0 0/2 * * * ?")
    //@SchedulerLock(name = "jobSendPicMail", lockAtMostFor = "60000", lockAtLeastFor = "50000")
    public void doSendPicMail()   {
        //此例在正式环境测试成功。注意须放到公网上测试
        log.info(StringUtil.getCurrentDateTime()+"开始向数据库中写入日志。。。。。。。。。。。");
        log .info("开始发带附件邮件：");
        String content="邮件正文标题";
        String smtpAddr = "smtp.163.com";
        String sendEmail = "xxx@163.com";
        String senderName = "众智益成";
        String senderMailLoginId = "xxx@163.com";
        String pwd =  "111111";//使用163邮箱的话，密码须申请授权码，不是登录密码
        //String pwd = FileUtil.getTextFileContent("d:\\pwd.txt", "utf-8");
        String mailTitle = "邮件标题111" ;
        //System.out.println("测试发邮件开始.........");
        try {
            Mail mail = new Mail(smtpAddr, sendEmail, senderName, senderMailLoginId,
                    pwd, "yyyyyy@qq.com", mailTitle, content, "465");
            mail.addAttachfile("/root/niu.png");
            log.info("开始发送邮件");
            mail.send();
            log.info("邮件发送完毕............");
            //System.out.println("开始发送邮件完成..w............");
        }
        catch(Exception ex11){
            //java.net.SocketException: Connection reset
            System.out.println("测试发邮件异常.........:"+ex11.toString());
        }
    }
}
