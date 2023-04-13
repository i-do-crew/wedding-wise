package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.Group;
import com.idocrew.weddingwise.entity.User;
import com.idocrew.weddingwise.entity.Vendor;
import com.idocrew.weddingwise.repositories.CustomerRepository;
import com.idocrew.weddingwise.repositories.UserGroupRepository;
import com.idocrew.weddingwise.repositories.UserRepository;
import com.idocrew.weddingwise.repositories.VendorRepository;
import com.idocrew.weddingwise.services.EmailService;
import com.idocrew.weddingwise.services.UserRegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
@AllArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRepository userRepository;
    private final VendorRepository vendorRepository;
    private final CustomerRepository customerRepository;

    private final UserGroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public void register(Customer customer) throws DuplicateKeyException {
        if(checkIfUserExist(customer.getEmail())){
            throw new DuplicateKeyException("User already exists for this email");
        }
        User userEntity = new User();
        Customer custEntity = new Customer();
        BeanUtils.copyProperties(customer, userEntity);
        BeanUtils.copyProperties(customer, custEntity);
        String hash = passwordEncoder.encode(customer.getPassword());
        userEntity.setPassword(hash);
        updateUserGroup(userEntity);
        customerRepository.save(custEntity);
        userRepository.save(userEntity);
        sendRegistrationConfirmationEmail(custEntity);
    }

    @Override
    public void register(Vendor vendor) {

    }

    private boolean checkIfUserExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    private void sendRegistrationConfirmationEmail(User userEntity) {
        emailService.sendWelcomeEmail(
            userEntity,
            "Welcome, [%s]".formatted(userEntity.getFirstName()),
            "You have been registered"
        );
    }

    private void updateUserGroup(User userEntity){
        Group group = groupRepository.findByCode("customer");
        userEntity.setUserGroup(group);
    }
}