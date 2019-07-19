package cn.zedognw.mymail;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;
import java.util.Date;
import java.util.Properties;

/**
 * @Author ZeDongW
 * @ClassName SendMail
 * @Description 自定义发送邮件
 * @Version 1.0
 * @date ：Created in 2019/7/19/0019 10:37
 * @modified By：
 */

public class SendMail {

    //设置邮件阐述
    static Properties properties = new Properties();

    //创建邮箱会话
    static Session session = null;

    //邮箱对象
    static MimeMessage message = null;

    //发件人
    static InternetAddress from = null;

    //收件人
    static InternetAddress to = null;

    static{
        properties.put("mail.transport.protocol", "smtp");	// 指定协议
        properties.put("mail.smtp.host", "localhost");		// 主机   stmp.qq.com
        properties.put("mail.smtp.port", 25);					// 端口
        properties.put("mail.smtp.auth", "true");				// 用户密码认证
        properties.put("mail.debug", "true");

        session = Session.getDefaultInstance(properties);

        message = new MimeMessage(session);

        try {
            from = new InternetAddress("wangwu@zedongw.cn");
            to = new InternetAddress("lisi@zedongw.cn");
        } catch (AddressException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * @Author: ZeDongW
     * @Description: 自定义发送文件
     * @Date: 2019/7/19/0019 10:38
     * @Param: []
     * @return: void
     */
    public void serdMail() throws MessagingException {

        //设置邮箱标题
        message.setSubject("第一封邮件");

        //设置邮件发送时间
        message.setSentDate(new Date());

        //发件人
        message.setSender(from);

        //收件人
        message.setRecipient(Message.RecipientType.TO, to);

        //内容
        message.setText("你好，发送邮件成功！ 正文。。。。。");

        //保存邮件
        message.saveChanges();

        //发送邮件
        Transport transport = session.getTransport();
        //用户名，密码
        transport.connect("wangwu", "wangwu");

        //发送邮件
        transport.sendMessage(message, message.getAllRecipients());

        //关闭会话
        transport.close();
    }

    //发送带图片的邮件
    public void sendMail_image() throws MessagingException {
        //创建邮件会话
        Session session = Session.getDefaultInstance(properties);

        //设置邮箱信息

        //标题
        message.setSubject("带图邮件");

        //发件人
        message.setSender(from);

        //收件人
        message.setRecipient(Message.RecipientType.TO, to);

        //时间
        message.setSentDate(new Date());

        //构建多功能邮件块
        MimeMultipart releted = new MimeMultipart("releted");

        //构建多功能邮件块内容  左：文本内特容 + 右：图片资源
        MimeBodyPart right = new MimeBodyPart();
        MimeBodyPart left = new MimeBodyPart();

        //设置具体内容
        String path = SendMail.class.getResource("/1.jpg").getPath();

        FileDataSource ds = new FileDataSource(new File(path));

        DataHandler dataHandler = new DataHandler(ds);

        left.setDataHandler(dataHandler);
        left.setContentID("1.jpg");  //设置资源名称

        //设置文本内容
        right.setContent("<img src='cid:1.jpg'/> 带图片的邮件", "text/html;charset=UTF-8");

        //邮件块的添加内容
        releted.addBodyPart(left);
        releted.addBodyPart(right);

        //复杂邮件添加到邮件块中
        message.setContent(releted);

        //发送
        Transport transport = session.getTransport();
        transport.connect("wangwu", "wangwu");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();

    }

    /**
     * @Author: ZeDongW
     * @Description: 带图片资源及附件的邮件
     * @Date: 2019/7/19/0019 11:25
     * @Param: []
     * @return: void
     */
    public void sendMail_imageAndData() throws MessagingException {
        //创建邮件会话
        Session session = Session.getDefaultInstance(properties);

        //设置邮箱信息

        //标题
        message.setSubject("带图片及附件的邮件");

        //发件人
        message.setSender(from);

        //收件人
        message.setRecipient(Message.RecipientType.TO, to);

        //时间
        message.setSentDate(new Date());

        //构建多功能邮件块
        MimeMultipart mixed = new MimeMultipart("mixed");

        //复杂邮件添加到邮件块中
        message.setContent(mixed);

        //构建多功能邮件块内容  左：文本内特容 + 右：图片资源
        MimeBodyPart left = new MimeBodyPart();
        MimeBodyPart right = new MimeBodyPart();

        //邮件块的添加内容
        mixed.addBodyPart(left);
        mixed.addBodyPart(right);

        //设置具体内容
        String path = SendMail.class.getResource("/1.docx").getPath();

        FileDataSource ds = new FileDataSource(new File(path));

        DataHandler dataHandler = new DataHandler(ds);

        right.setDataHandler(dataHandler);
        right.setFileName("1.docx");  //设置资源名称

        //构建多功能邮件块
        MimeMultipart releted = new MimeMultipart("releted");

        //设置到总邮件左边
        left.setContent(releted);

        //构建多功能邮件块内容  左：文本内特容 + 右：图片资源
        MimeBodyPart content = new MimeBodyPart();
        MimeBodyPart resource = new MimeBodyPart();


        //设置具体内容
        String path1 = SendMail.class.getResource("/1.jpg").getPath();

        FileDataSource ds1 = new FileDataSource(new File(path1));

        DataHandler dataHandler1 = new DataHandler(ds1);

        resource.setDataHandler(dataHandler1);
        resource.setContentID("1.jpg");  //设置资源名称

        //设置文本内容
        content.setContent("<img src='cid:1.jpg'/> 带图片的邮件", "text/html;charset=UTF-8");

        releted.addBodyPart(content);
        releted.addBodyPart(resource);

        //发送
        Transport transport = session.getTransport();
        transport.connect("wangwu", "wangwu");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();

    }
}
