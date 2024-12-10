package com.example;

import java.util.Random;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class BulkSMSSender {

    public static final String ACCOUNT_SID = "AC50690bd041d9352a35cb54b0015d10d6";
    public static final String AUTH_TOKEN = "299db6cd6bac4657ac55219b276e5af1";
    public static final String FROM_NUMBER = "+84563392100";

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String[] recipients = { "+13055405076" };

        String messageBody = "Xin chào! Đây là tin nhắn từ thương hiệu của bạn.";

        for (String to : recipients) {
            sendSMS(to, messageBody);
            try {
                Thread.sleep(1000 + new Random().nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Đã gửi tất cả SMS.");
    }

    private static void sendSMS(String to, String body) {
        try {
            Message message = Message.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(FROM_NUMBER),
                    body).create();
            System.out.println("Gửi thành công đến: " + to);
        } catch (Exception e) {
            System.out.println("Gửi thất bại đến: " + to);
            e.printStackTrace();
        }
    }
}
