package org.openjweb.core.mail;

import org.openjweb.common.util.StringUtil;
import java.util.* ;
import javax.mail.* ;
import javax.mail.internet.* ;
import javax.activation.* ;

public class Mail {
    //定义发件人、收件人、SMTP服务器、用户名、密码、主题、内容等
    private String displayName;
    private String to;
    private String from;
    private String smtpServer;
    private String username;
    private String password;
    private String subject;
    private String content;
    private boolean ifAuth; //服务器是否要身份认证
    private String filename="";
    private Vector file = new Vector(); //用于保存发送附件的文件名的集合
    private String port="25";//smtp端口
    private final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";


    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    /**
     * 设置SMTP服务器地址
     */
    public void setSmtpServer(String smtpServer){
        this.smtpServer=smtpServer;
    }

    /**
     * 设置发件人的地址
     */
    public void setFrom(String from){
        this.from=from;
    }
    /**
     * 设置显示的名称
     */
    public void setDisplayName(String displayName){
        this.displayName=displayName;
    }

    /**
     * 设置服务器是否需要身份认证
     */
    public void setIfAuth(boolean ifAuth){
        this.ifAuth=ifAuth;
    }

    /**
     * 设置E-mail用户名
     */
    public void setUserName(String username){
        this.username=username;
    }

    /**
     * 设置E-mail密码
     */
    public void setPassword(String password){
        this.password=password;
    }

    /**
     * 设置接收者
     */
    public void setTo(String to){
        this.to=to;
    }

    /**
     * 设置主题
     */
    public void setSubject(String subject){
        this.subject=subject;
    }

    /**
     * 设置主体内容
     */
    public void setContent(String content){
        this.content=content;
    }

    /**
     * 该方法用于收集附件名
     */
    public void addAttachfile(String fname){
        file.addElement(fname);
    }

    public Mail(){

    }

    /**
     * 初始化SMTP服务器地址、发送者E-mail地址、用户名、密码、接收者、主题、内容
     */
    public Mail (String smtpServer,String from,String displayName,String username,String password,String to,String subject,String content){
        this.smtpServer=smtpServer;
        this.from=from;
        this.displayName=displayName;
        this.ifAuth=true;
        this.username=username;
        this.password=password;
        this.to=to;
        this.subject=subject;
        this.content=content;
    }

    /**
     * 初始化SMTP服务器地址、发送者E-mail地址、用户名、密码、接收者、主题、内容  ，端口
     */
    public Mail (String smtpServer,String from,String displayName,String username,String password,String to,String subject,String content,String port){
        this.smtpServer=smtpServer;
        this.from=from;
        this.displayName=displayName;
        this.ifAuth=true;
        this.username=username;
        this.password=password;
        this.to=to;
        this.subject=subject;
        this.content=content;
        this.port = port;
    }

    /**
     * 初始化SMTP服务器地址、发送者E-mail地址、接收者、主题、内容
     */
    public Mail(String smtpServer,String from,String displayName,String to,String subject,String content){
        this.smtpServer=smtpServer;
        this.from=from;
        this.displayName=displayName;
        this.ifAuth=false;
        this.to=to;
        this.subject=subject;
        this.content=content;
    }

    /**
     * 发送邮件
     * @throws Exception
     */
    public HashMap send() throws Exception
    {

        System.setProperty("java.net.preferIPv4Stack", "true"); //DEBUG SMTP: trying to connect to host "127.0.0.1", port 25, isSSL false Could not connect to SMTP host: 127.0.0.1, port: 25;


        HashMap map=new HashMap();
        map.put("state", "success");
        String message="邮件发送成功！";
        Session session=null;
        Properties props = System.getProperties();
        props.put("mail.smtp.host", smtpServer);
        props.put("mail.smtp.port", port);
        // if(ifAuth){ //服务器需要身份认证
        if(1==1){ //服务器需要身份认证

            props.put("mail.smtp.auth","true");
            SmtpAuth smtpAuth=new SmtpAuth(username,password);
            session=Session.getDefaultInstance(props, smtpAuth);
        }else{
            props.put("mail.smtp.auth","false");
            session=Session.getDefaultInstance(props, null);
        }

        //这里暂时增加只处理SSL的开始，以后再把是否SSL做到参数传入，不过目前阿里云服务器只能使用465的SSL模式发邮件
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);  //使用JSSE的SSL socketfactory来取代默认的socketfactory
        props.put("mail.smtp.socketFactory.fallback", "false");  // 只处理SSL的连接,对于非SSL的连接不做处理
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.ssl.enable", true);//这句漏掉了导致SSL发送老提示连不上
        props.put("mail.transport.protocol", "smtps"); // 启用smtps方式

        //这里暂时增加只处理SSL的结束
        session.setDebug(false);
        Transport trans = null;
        try {
            Message msg = new MimeMessage(session);
            try{
                Address from_address = new InternetAddress(from, displayName);
                msg.setFrom(from_address);
            }catch(java.io.UnsupportedEncodingException e){
                e.printStackTrace();
            }
            InternetAddress[] address={new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO,address);
            //msg.setSubject(subject);   //这个地方加个日期时分秒，可能会防止被认为是垃圾邮件
            msg.setSubject(subject+" "+ StringUtil.getCurrentDateTime());
            Multipart mp = new MimeMultipart();
            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setContent(content.toString(), "text/html;charset=gb2312");
            mp.addBodyPart(mbp);
            if(!file.isEmpty()){//有附件
                Enumeration efile=file.elements();
                while(efile.hasMoreElements()){
                    mbp=new MimeBodyPart();
                    filename=efile.nextElement().toString(); //选择出每一个附件名
                    FileDataSource fds=new FileDataSource(filename); //得到数据源
                    mbp.setDataHandler(new DataHandler(fds)); //得到附件本身并至入BodyPart
                    mbp.setFileName(MimeUtility.encodeText(filename));//这里的GB2312我认为应该和161行的设置称一致

                    //
                    mp.addBodyPart(mbp);

                }
                file.removeAllElements();
            }
            msg.setContent(mp); //Multipart加入到信件
            msg.setSentDate(new Date());     //设置信件头的发送日期
            //发送信件
            msg.saveChanges();

            trans = session.getTransport("smtp");
            trans.connect(smtpServer, username, password);


            trans.sendMessage(msg, msg.getAllRecipients());

            trans.close();

        }catch(AuthenticationFailedException e){
            map.put("state", "failed");
            message="邮件发送失败！错误原因：\n"+"身份验证错误!";
            throw new Exception("邮箱认证失败!");
            //e.printStackTrace();
        }catch (MessagingException e) {
            message="邮件发送失败！错误原因：\n"+e.getMessage();
            map.put("state", "failed");

            throw new Exception("MessagingException:"+e.toString());
        }
        //System.out.println("\n提示信息:"+message);
        map.put("message", message);
        return map;
    }

    public static void main(String args[]) throws Exception
    {
        String content="<html><body><h2>SpringBoot学习<h2></body></html>";
        String smtpAddr = "smtp.163.com";
        String sendEmail = "xxx@163.com";
        String senderName = "openjweb";
        String senderMailLoginId = "xxx@163.com";
        String pwd = "111111";
        String toMail = "yyy@qq.com";//接收者邮箱
        String mailTitle = "我再次给自己发了个邮件";
        Mail mail = new Mail(smtpAddr,sendEmail,senderName,senderMailLoginId,
                pwd,toMail,mailTitle,content,"465");
        mail.addAttachfile("d:\\pic\\water.png");
        mail.send();
    }


}
