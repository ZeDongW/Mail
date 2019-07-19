package cn.zedognw.mymail;

import org.junit.Test;

import javax.mail.MessagingException;

import static org.junit.Assert.*;

public class SendMailTest {
    SendMail sendMail = new SendMail();

    @Test
    public void serdMail() throws MessagingException {
        sendMail.serdMail();
    }

    @Test
    public void sendMail_image() throws MessagingException {
        sendMail.sendMail_image();
    }

    @Test
    public void sendMail_imageAndData() throws MessagingException {
        sendMail.sendMail_imageAndData();
    }
}