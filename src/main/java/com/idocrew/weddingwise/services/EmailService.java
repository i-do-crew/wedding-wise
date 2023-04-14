package com.idocrew.weddingwise.services;


import com.idocrew.weddingwise.entity.User;

public interface EmailService {
    void sendVerificationRequest(User user);
}
