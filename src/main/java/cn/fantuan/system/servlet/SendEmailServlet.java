package cn.fantuan.system.servlet;

import cn.fantuan.system.uitil.CodeUtil;
import cn.fantuan.system.uitil.Email;
import cn.fantuan.system.uitil.HtmlText;
import org.springframework.stereotype.Service;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class SendEmailServlet {
    private String email;
    private String code;
    private String error = "获取验证码失败！";

    public String sendEmail(String user) {
        email = user;
        if (email != null) {
            System.out.println("邮箱发送功能:");
            try {
                System.out.println(email);
                Email.receiveMailAccount = email;
                Properties props = new Properties();
                props.setProperty("mail.debug", "true");
                props.setProperty("mail.smtp.auth", "true");
                props.setProperty("mail.host", Email.emailSMTPHost);
                props.setProperty("mail.transport.protocol", "smtp");
                Session session = Session.getInstance(props);
                session.setDebug(true);
                code = CodeUtil.Code(6);
                System.out.println("邮箱验证码:" + code);
                String html = HtmlText.html(code);
                MimeMessage message = Email.creatMimeMessage(session, Email.emailAccount, Email.receiveMailAccount, html);
                Transport transport = session.getTransport();
                transport.connect(Email.emailAccount, Email.emailPassword);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                return code;
            } catch (Exception e) {
                e.printStackTrace();
                return code;
            }
        } else {
            return error;
        }
    }
}
