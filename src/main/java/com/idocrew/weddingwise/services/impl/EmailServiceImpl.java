package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.User;
import com.idocrew.weddingwise.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("emailService")
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.from}")
    private String from;

    @Override
    public void sendVerificationRequest(User user) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Welcome, [%s]".formatted(user.getFirstName()));
        helper.setTo(user.getEmail());

        String htmlEmailBody = "You have been registered";
        helper.setText(htmlEmailBody, true);
        try {
            javaMailSender.send(mimeMessage);
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
    }

}
