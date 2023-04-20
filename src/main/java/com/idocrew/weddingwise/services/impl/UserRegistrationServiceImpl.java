package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.context.AccountVerificationEmailContext;
import com.idocrew.weddingwise.entity.*;
import com.idocrew.weddingwise.repositories.*;
import com.idocrew.weddingwise.services.EmailService;
import com.idocrew.weddingwise.services.SecureTokenService;
import com.idocrew.weddingwise.services.UserRegistrationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service("userService")
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRepository userRepository;
    private final VendorRepository vendorRepository;
    private final CustomerRepository customerRepository;
    private final UserGroupRepository principalGroupRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final SecureTokenService secureTokenService;
    private final SecureTokenRepository secureTokenRepository;
    @Value("${site.base.url.https}")
    private String baseURL;
    @Override
    @Transactional
    public void register(Customer customer) throws DuplicateKeyException {
        if(checkIfUserExist(customer.getUser().getEmail())){
            throw new DuplicateKeyException("User already exists for this email");
        }
        User userEntity = new User();
        Customer custEntity = new Customer();
        BeanUtils.copyProperties(customer.getUser(), userEntity);
        BeanUtils.copyProperties(customer, custEntity);
        String hash = passwordEncoder.encode(customer.getUser().getPassword());
        userEntity.setPassword(hash);
        userEntity.setUsername(customer.getUser().getEmail());
        addUserGroup(userEntity, "CUSTOMER");
        userEntity = userRepository.save(userEntity);
        custEntity.setUser(userEntity);
        customerRepository.save(custEntity);
        sendRegistrationConfirmationEmail(userEntity);
    }

    @Override
    @Transactional
    public void register(VendorComposite vendorComposite) {
        Vendor vendor = vendorComposite.getVendor();
        User user = vendor.getUser();
        VendorCategory vendorCategory = vendorComposite.getVendorCategory();
        vendorCategory.getVendors().add(vendor);
        Venue venue = vendorComposite.getVenue();
        PhotoFormat photoFormat = vendorComposite.getPhotoFormat();
        MusicType djsAndLiveBandsCategory = vendorComposite.getMusicType();
        MusicGenre musicGenre = vendorComposite.getMusicGenre();

        if(checkIfUserExist(user.getEmail())){
            throw new DuplicateKeyException("User already exists for this email");
        }
        User userEntity = new User();
        Vendor vendorEntity = new Vendor();
        BeanUtils.copyProperties(user, userEntity);
        BeanUtils.copyProperties(vendor, vendorEntity);
        String hash = passwordEncoder.encode(user.getPassword());
        userEntity.setPassword(hash);
        userEntity.setUsername(user.getEmail());
        addUserGroup(userEntity, "VENDOR");
        userEntity = userRepository.save(userEntity);
        vendorEntity.setUser(userEntity);
        vendorEntity = vendorRepository.save(vendorEntity);
    }

    private boolean checkIfUserExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    private void sendRegistrationConfirmationEmail(User userEntity) {
        SecureToken secureToken = secureTokenService.createSecureToken(userEntity);

        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(userEntity);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseURL, secureToken.getToken());
        try {
            emailService.sendVerificationEmail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void addUserGroup(User userEntity, String code){
        Group principalGroup =  principalGroupRepository.findByCode(code);
        userEntity.setUserGroups(List.of(principalGroup));
    }
}