package cn.fantuan.system.util;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;

public class Email {
    // 发件人邮箱
    public static String emailAccount = "1317524710@qq.com";
    // 发件人的授权码
    public static String emailPassword = "tuhdwluddbexjcch";
    //发件人的邮箱服务 地址
    public static String emailSMTPHost = "smtp.qq.com";
    //  收件人邮箱
    public static String receiveMailAccount = "";

    /**
     * 创建一封邮件(发件人，收件人，邮件内容)
     *
     * @param session
     * @param sendMail
     * @param receiveMail
     * @param html
     * @return
     * @throws MessagingException
     * @throws IOException        cc:抄送、Bcc:密送、To:发送
     */
    public static MimeMessage creatMimeMessage(Session session, String sendMail, String receiveMail, String html) throws MessagingException, IOException {
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // From:发件人
        message.setFrom(new InternetAddress(sendMail, "灰姑娘的水晶鞋", "UTF-8"));
        // To：收件人（可以增加多个收件人：抄送或者密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "小灰", "UTF-8"));
        // Subject：邮件主题
        message.setSubject("邮箱验证", "UTF-8");
        // Content：邮件正文（可以使用html标签）
        message.setContent(html, "text/html;charset=UTF-8");
        // 设置发送时间
        message.setSentDate(new Date());
        // 保存设置
        message.saveChanges();
        return message;
    }
}
