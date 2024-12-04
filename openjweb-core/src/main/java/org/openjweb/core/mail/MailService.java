package org.openjweb.core.mail;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openjweb.core.entity.CommUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Service
@Slf4j
public class MailService {
    @Resource
    JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    @Resource
    TemplateEngine templateEngine;


    public boolean sendMailTo(String emailAddr,String subject,String content,String fileName,String filePath) throws MessagingException {
        //this.mailSender.
        // SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        // MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);// 发送带附件的
        try {
            message.setFrom(from);
            message.setSubject(subject);
            message.setTo(emailAddr);
            message.setText(content);
            if(filePath!=null&&filePath.trim().length()>0) {
                message.addAttachment(fileName, new File(filePath));// 发附件
            }
            // message.addAttachment(emailAddr, null);
            this.mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean sendMailWithImg(String from, String to, String subject, String content, String[] imgPath,
                                   String[] resIds) throws MessagingException {
        if (imgPath == null || imgPath.length == 0) {
            return false;
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);// 发送带附件的
        message.setFrom(from);
        message.setSubject(subject);
        message.setTo(to);
        message.setText(content, true);// 表示发送的是html

        for (int i = 0; i < imgPath.length; i++) {
            FileSystemResource res = new FileSystemResource(new File(imgPath[i]));
            message.addInline(resIds[i], res);

        }
        mailSender.send(mimeMessage);
        return false;
    }


    public boolean sendFreemarkerMail(String from, String to, String subject) throws MessagingException,
            TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);// freemarker 的Configuration
        ClassLoader loader = MailService.class.getClassLoader();
        // 注意设置templates以外的目录不起作用，原因是可能是freemarker默认使用的这个目录，需要开发配置类修改默认目录。
        configuration.setClassLoaderForTemplateLoading(loader, "templates");

        Template template = configuration.getTemplate("email.ftl", "utf-8");// 邮件模板
        StringWriter mail = new StringWriter();
        CommUser user = new CommUser();
        user.setUserSex("男");
        user.setRealName("张三");//测试
        template.process(user, mail);
        String content = mail.toString();// 转换为正文
        // 下面的和之前的一样
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);// 发送带附件的
        message.setFrom(from);
        message.setSubject(subject);
        message.setTo(to);
        message.setText(content, true);// 表示发送的是html

        mailSender.send(mimeMessage);

        return true;
    }

    public boolean sendMailThymeLeaf(String from, String to, String subject) throws MessagingException {

        Context ctx = new Context();// thymeleaf
		ctx.setVariable("realName", "张三");
		ctx.setVariable("userSex", "男");
		//String content = templateEngine.process("/mailtempl", ctx);//放templates目录不好用
		//String content = templateEngine.process("/mail/mailtempl.html", ctx);//.html扩展名可以不写
		//String content = templateEngine.process("/mail/mailtempl", ctx);//.html扩展名可以不写
        //注意不加/ ,如果加的话会找不到模板！
        String content = templateEngine.process("mail/mailtempl", ctx);//.html扩展名可以不写

        log.info("thymeleaf邮件正文::");
        log.info(content);
		// 下面的和之前的一样
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);// 发送带附件的
		message.setFrom(from);
		message.setSubject(subject);
		message.setTo(to);
		message.setText(content, true);// 表示发送的是html
		mailSender.send(mimeMessage);
        return true;
    }

}
