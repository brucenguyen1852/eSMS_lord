package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class BulkEmailSender {

    // Thông tin đăng nhập email
    private static final String USERNAME = "your_email@example.com";
    private static final String PASSWORD = "your_email_password";

    public static void main(String[] args) {
        List<String> recipients = Arrays.asList(
                "recipient1@example.com",
                "recipient2@example.com",
                "recipient3@example.com");

        String subject = "Thông Báo Từ Thương Hiệu Của Bạn";
        String body = "Xin chào,\n\nĐây là thông điệp tự động từ thương hiệu của bạn.\n\nTrân trọng!";

        for (String recipient : recipients) {
            sendEmail(recipient, subject, body);
            try {
                Thread.sleep(1000 + new Random().nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Đã gửi tất cả email.");
    }

    private static void sendEmail(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.example.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME, "Tên Thương Hiệu"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Gửi thành công đến: " + to);

        } catch (Exception e) {
            System.out.println("Gửi thất bại đến: " + to);
            e.printStackTrace();
        }
    }
}