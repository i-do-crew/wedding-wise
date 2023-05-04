package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    User findUserById(long id);
    User findByUsername(String username);
    User saveUser(User User);
    void deleteUser(User User);
    void editUserProfile(HttpServletRequest request, User user);
}
