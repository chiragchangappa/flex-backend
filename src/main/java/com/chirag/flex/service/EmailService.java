package com.chirag.flex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetEmail(String to, String link) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setSubject("Reset Password");
            msg.setText("Click here to reset your password: " + link);

            mailSender.send(msg);

            System.out.println("EMAIL SENT SUCCESSFULLY");

        } catch (Exception e) {
            System.out.println("EMAIL FAILED: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Email sending failed");
        }
    }
}