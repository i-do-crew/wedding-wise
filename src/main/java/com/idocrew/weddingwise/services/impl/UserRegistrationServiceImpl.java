package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.Group;
import com.idocrew.weddingwise.entity.User;
import com.idocrew.weddingwise.repositories.UserGroupRepository;
import com.idocrew.weddingwise.repositories.UserRepository;
import com.idocrew.weddingwise.services.EmailService;
import com.idocrew.weddingwise.services.UserRegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
@AllArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRepository userRepository;
    private final UserGroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public void register(User user) throws DuplicateKeyException {
        if(checkIfUserExist(user.getEmail())){
            throw new DuplicateKeyException("User already exists for this email");
            // TODO: throw new UserAlreadyExistException("User already exists for this email");
        }
        User userEntity = new User();
        BeanUtils.copyProperties(user, userEntity);
        String hash = passwordEncoder.encode(user.getPassword());
        userEntity.setPassword(hash);
        updateUserGroup(userEntity);
        userRepository.save(userEntity);
        sendRegistrationConfirmationEmail(userEntity);
    }

    private boolean checkIfUserExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    private void sendRegistrationConfirmationEmail(User userEntity) {
        emailService.sendWelcomeEmail(
            userEntity,
            "Welcome [%s]".formatted(userEntity.getFirstName()),
            "You have been registered"
        );
    }

    private void updateUserGroup(User userEntity){
        Group group = groupRepository.findByCode("CUSTOMER");
        userEntity.setUserGroups(List.of(group));
    }
}