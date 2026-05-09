package com.chirag.flex.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    private static final String API_KEY = System.getenv("SENDGRID_API_KEY");

    public void sendResetEmail(String to, String link) {

    	Email from = new Email("chiragchangappa02@gmail.com");
        Email recipient = new Email(to);

        String subject = "Reset Password";

        Content content = new Content(
                "text/plain",
                "Click here to reset your password: " + link
        );

        Mail mail = new Mail(from, subject, recipient, content);

        SendGrid sg = new SendGrid(API_KEY);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            System.out.println("SendGrid Status: " + response.getStatusCode());

        } catch (IOException e) {
            throw new RuntimeException("SendGrid email failed", e);
        }
    }
}