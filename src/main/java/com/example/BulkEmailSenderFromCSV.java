package com.example;

import java.io.*;
import java.util.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class BulkEmailSenderFromCSV {

    private static final String USERNAME = "your_email@example.com";
    private static final String PASSWORD = "your_email_password";

    public static void main(String[] args) {
        String csvFile = "recipients.csv";
        String line = "";
        String cvsSplitBy = ",";
        List<Recipient> recipients = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                if (data.length >= 2) {
                    recipients.add(new Recipient(data[0], data[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String subject = "Thông Báo Từ Thương Hiệu Của Bạn";
        String bodyTemplate = "Xin chào %s,\n\nĐây là thông điệp tự động từ thương hiệu của bạn.\n\nTrân trọng!";

        for (Recipient recipient : recipients) {
            String body = String.format(bodyTemplate, recipient.getName());
            sendEmail(recipient.getEmail(), subject, body);
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

    static class Recipient {
        private String email;
        private String name;

        public Recipient(String email, String name) {
            this.email = email;
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }
    }
}
