package org.mixed.exam.teacher.service;

import org.mixed.exam.teacher.pojo.dto.EmailDto;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class EmailService {
    private Session session;

    private Session getSession() {
        if (session != null) {
            return session;
        }
        // 定义邮箱服务器配置
        Properties properties = new Properties();
        // 设置邮件服务器主机名 （163 邮件服务器地址："mail.smtp.host" "smtp.163.com"）
        properties.setProperty("mail.smtp.host", "smtp.qq.com");
        // 设置邮件服务器的端⼝
        properties.setProperty("mail.smtp.port", "587");
        // 设置邮件服务器认证属性 （设置为true表示发送服务器需要身份验证）
        properties.setProperty("mail.smtp.auth", "true");

        // 使⽤JavaMail发送邮件的5个步骤
        // 1. 创建session
        session = Session.getInstance(properties);
        session.setDebug(true);
        return session;
    }

    public void send(EmailDto emailDto) {
        try {
            // 2. 通过session得到transport对象
            Transport ts = getSession().getTransport();
            // 3. 使⽤邮箱的⽤户名和密码连上邮件服务器（⽤户名只需写@前⾯的即可，密码是指授权码）
            ts.connect("smtp.qq.com", "281876918", "mnymitmxdvvabgij");
            // 4. 创建邮件
            // 发送包含附件的邮件
            Message message = createMail(getSession(), emailDto);
            // 5. 发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            // 关闭transport对象
            ts.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(List<EmailDto> emailDtos) {
        Transport ts;
        try {
            ts = getSession().getTransport();
            ts.connect("smtp.qq.com", "281876918", "mnymitmxdvvabgij");
        } catch (MessagingException e) {
            e.printStackTrace();
            return;
        }

        for (EmailDto emailDto : emailDtos) {
            try {
                // 4. 创建邮件
                // 发送包含附件的邮件
                Message message = createMail(getSession(), emailDto);
                // 5. 发送邮件
                ts.sendMessage(message, message.getAllRecipients());
            } catch (MessagingException e) {
                System.out.println("邮箱格式不对,发送失败!");
                continue;
            }
        }
        try {
            ts.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public MimeMessage createMail(Session session, EmailDto emailDto) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("281876918@qq.com"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailDto.getTo()));
        message.setSubject(emailDto.getSubject());
        message.setSentDate(new Date());
        message.setText(emailDto.getContent());
        // 返回创建好的邮件对象
        return message;
    }
}
