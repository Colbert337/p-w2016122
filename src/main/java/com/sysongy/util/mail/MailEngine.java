package com.sysongy.util.mail;


import com.sysongy.util.AliShortMessage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Map;

/**
 * Created by Administrator on 2016/5/27.
 */
public class MailEngine {

    private static Logger logger = LoggerFactory.getLogger(AliShortMessage.class);

    private FreeMarkerConfigurer freeMarkerConfigurer;

    private MailSender mailSender;

    public FreeMarkerConfigurer getFreeMarkerConfigurer() {
        return freeMarkerConfigurer;
    }

    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    public MailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }


    /**
     * 发送简单邮件
     * @param msg
     */
    public void send(SimpleMailMessage msg) {
        try {
            ((JavaMailSenderImpl) mailSender).send(msg);
        } catch (MailException ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * 通过模板产生邮件正文
     * @param templateName    邮件模板名称
     * @param map            模板中要填充的对象
     * @return 邮件正文（HTML）
     */
    public String generateEmailContent(String templateName, Map map) {
        //使用FreeMaker模板
        try {
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template t = configuration.getTemplate(templateName);
            return FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
        } catch (TemplateException e) {
            logger.error("Error while processing FreeMarker template ", e);
        } catch (FileNotFoundException e) {
            logger.error("Error while open template file ", e);
        } catch (IOException e) {
            logger.error("Error while generate Email Content ", e);
        }
        return null;
    }

    /**
     * 发送邮件
     * @param emailAddress      收件人Email地址的数组
     * @param fromEmail         寄件人Email地址, null为默认寄件人web@vnvtrip.com
     * @param bodyText          邮件正文
     * @param subject           邮件主题
     * @param attachmentName    附件名
     * @param resource          附件
     * @throws MessagingException
     */
    public void sendMessage(String[] emailAddresses, String fromEmail,
                            String bodyText, String subject, String attachmentName,
                            ClassPathResource resource) throws MessagingException {
        logger.info("********************send the mail begin: receiver =" + emailAddresses + "Subject=" + subject + "*******************");
        MimeMessage message = ((JavaMailSenderImpl) mailSender).createMimeMessage();
        // use the true flag to indicate you need a multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(emailAddresses);
        if(fromEmail != null){
            helper.setFrom(fromEmail);
        }
        helper.setText(bodyText, true);
        helper.setSubject(subject);
        if(attachmentName!=null && resource!=null)
            helper.addAttachment(attachmentName, resource);
        ((JavaMailSenderImpl) mailSender).send(message);
        logger.info("********************send the mail end!*******************");
    }

    /**
     * 使用模版发送HTML格式的邮件
     *
     * @param msg          装有to,from,subject信息的SimpleMailMessage
     * @param templateName 模版名,模版根路径已在配置文件定义于freemakarengine中
     * @param model        渲染模版所需的数据
     */
    public void send(SimpleMailMessage msg, String templateName, Map model) {
        logger.info("********************send the mail begin: receiver =" + msg.getTo() + "Subject=" + msg.getSubject() + "*******************");
        String content = generateEmailContent(templateName, model);         //生成html邮件内容
        MimeMessage mimeMsg = null;
        try {
            mimeMsg = ((JavaMailSenderImpl) mailSender).createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true, "utf-8");
            helper.setTo(msg.getTo());
            if(msg.getSubject()!=null)
                helper.setSubject(msg.getSubject());
            if(msg.getFrom()!=null)
                helper.setFrom(msg.getFrom());
            helper.setText(content, true);
            ((JavaMailSenderImpl) mailSender).send(mimeMsg);
            logger.info("********************send the mail end!*******************");
        } catch (MessagingException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    //注入mailEngine 和 mailMessage，调用以下方法发送HTML邮件
    public static void main(String[] args){
        /**
        mailMessage.setTo("chendong<dong.chen@sysongy.com>");
        mailMessage.setSubject("我想吃饭的邮件服务器");
        Map<String, String> model = new HashMap<String, String>();
        model.put("user", "12345");
        model.put("password", "432123");
        mailEngine.send(mailMessage, "loginMail.ftl", model);
         **/
    }
}

