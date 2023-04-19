package com.idocrew.weddingwise.services;


import com.idocrew.weddingwise.entities.User;

public interface EmailService {
    void prepareAndSend(User user, String subject, String body);
    void sendWelcomeEmail(User user, String subject, String body);
}
