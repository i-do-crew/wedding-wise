package com.idocrew.weddingwise.services;


import com.idocrew.weddingwise.entity.User;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendVerificationRequest(User user) throws MessagingException;
}
