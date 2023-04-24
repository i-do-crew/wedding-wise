package com.idocrew.weddingwise.services;


import com.idocrew.weddingwise.context.AbstractEmailContext;
import com.idocrew.weddingwise.entity.User;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendVerificationEmail(final AbstractEmailContext email) throws MessagingException;
    void sendWelcomeEmail(User user, String subject, String body);
}
