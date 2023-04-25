package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
