package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.User;

public interface UserService {
    User findUserById(long id);
    User findByUsername(String username);
    User saveUser(User User);
    void deleteUser(User User);
}
