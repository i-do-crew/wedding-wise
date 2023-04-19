package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.User;
import com.idocrew.weddingwise.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("emailService")
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    @Value("${spring.mail.from}")
    private String from;

    @Override
    public void prepareAndSend(User user, String subject, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(user.getEmail());
        msg.setSubject(subject);
        msg.setText(body);
        try {
            emailSender.send(msg);
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendWelcomeEmail(User user, String subject, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(user.getEmail());
        msg.setSubject(subject);
        msg.setText(body);
        try {
            emailSender.send(msg);
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
    }
}
