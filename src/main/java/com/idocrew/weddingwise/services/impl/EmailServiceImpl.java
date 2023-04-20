package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.context.AbstractEmailContext;
import com.idocrew.weddingwise.entity.User;
import com.idocrew.weddingwise.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Service("emailService")
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    @Value("${spring.mail.from}")
    private String from;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void sendVerificationEmail(AbstractEmailContext email) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(email.getContext());
        String emailContent = templateEngine.process(email.getTemplateLocation(), context);

        mimeMessageHelper.setTo(email.getTo());
        mimeMessageHelper.setSubject(email.getSubject());
        mimeMessageHelper.setFrom(email.getFrom());
        mimeMessageHelper.setText(emailContent, true);
        emailSender.send(message);
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
