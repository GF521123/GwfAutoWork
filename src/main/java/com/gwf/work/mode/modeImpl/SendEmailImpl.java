package com.gwf.work.mode.modeImpl;

import com.gwf.work.mode.SendEmail;
import com.gwf.work.entity.ToEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Arrays;

/**
 * @author GWF
 */

@Repository("SendEmail")
public class SendEmailImpl implements SendEmail {
    private static final Logger log = LoggerFactory.getLogger(SendEmailImpl.class);

    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${ToEmail.top}")
    private String[] tos;


    /*
     * 普通邮件发送 文本toEmail.Content不变发送
     */
    public String commonEmail(ToEmail toEmail) {
        toEmail.setTos(tos);
        // 创建简单邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        // 谁发的
        message.setFrom(from);
        // 谁要接收
        message.setTo(toEmail.getTos());
        // 邮件标题
        message.setSubject(toEmail.getSubject());
        // 邮件内容
        message.setText(toEmail.getContent());
        try {
            mailSender.send(message);
            return "发送邮件成功";
        } catch (MailException e) {
            e.printStackTrace();
            return "发送邮件方失败";
        }
    }

    /*
     * html文本邮件发送 toEmail.Content 以html格式
     */
    public String htmlEmail(ToEmail toEmail) {
        toEmail.setTos(tos);
        log.info("【邮件】发送邮件给" + Arrays.toString(toEmail.getTos()) + "........");
        // 创建一个MINE消息
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper minehelper = new MimeMessageHelper(message, true);
            // 谁发
            minehelper.setFrom(from);
            // 谁要接收
            minehelper.setTo(toEmail.getTos());
            // 邮件主题
            minehelper.setSubject(toEmail.getSubject());
            // 邮件内容 true 表示带有附件或html
            minehelper.setText(toEmail.getContent(), true);
            mailSender.send(message);
            return "【邮件】发送邮件成功";
        } catch (MailException | MessagingException e) {
            e.printStackTrace();
            return "【邮件】发送邮件失败";
        }
    }

    /*
     * 带文件
     * 未测试
     */
    public String staticEmail(ToEmail toEmail, MultipartFile multipartFile, String resId) {
        toEmail.setTos(tos);
        //创建一个MINE消息
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //谁发
            helper.setFrom(from);
            //谁接收
            helper.setTo(toEmail.getTos());
            //邮件主题
            helper.setSubject(toEmail.getSubject());
            //邮件内容   true 表示带有附件或html
            //邮件内容拼接
            String content =
                    "<html><body><img width='250px' src=\'cid:" + resId + "\'>" + toEmail.getContent()
                            + "</body></html>";
            helper.setText(content, true);
            //蒋 multpartfile 转为file
            File multipartFileToFile = MultipartFileToFile(multipartFile);
            FileSystemResource res = new FileSystemResource(multipartFileToFile);

            //添加内联资源，一个id对应一个资源，最终通过id来找到该资源
            helper.addInline(resId, res);
            mailSender.send(message);
            return "嵌入静态资源的邮件已经发送";
        } catch (MessagingException e) {
            return "嵌入静态资源的邮件发送失败";
        }
    }

    private File MultipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若需要防止生成的临时文件重复,可以在文件名后添加随机码

        try {
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
