package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.*;
import com.idocrew.weddingwise.repositories.*;
import com.idocrew.weddingwise.services.EmailService;
import com.idocrew.weddingwise.services.UserRegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service("userService")
@AllArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRepository userRepository;
    private final VendorRepository vendorRepository;
    private final CustomerRepository customerRepository;
    private final PrincipalGroupRepository principalGroupRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final VendorCategoryRepository vendorCategoryRepository;
    private final VenueRepository venueRepository;
    private final DjsAndLiveBandsCategoryRepository djsAndLiveBandsCategoryRepository;
    private final MusicGenreRepository musicGenreRepository;

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
        //sendRegistrationConfirmationEmail(userEntity);
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
        VendorsPhotoFormat vendorsPhotoFormat = new VendorsPhotoFormat();
        vendorsPhotoFormat.setPhotoFormat(photoFormat);
        DjsAndLiveBandsCategory djsAndLiveBandsCategory = vendorComposite.getDjsAndLiveBandsCategory();
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

        vendorEntity.getVendorsPhotoFormats().add(vendorsPhotoFormat);

        vendorEntity = vendorRepository.save(vendorEntity);

        vendorCategory.getVendors().add(vendorEntity);
        vendorCategoryRepository.save(vendorCategory);

        venue.setVendor(vendorEntity);
        venueRepository.save(venue);

        djsAndLiveBandsCategoryRepository.save(djsAndLiveBandsCategory);
        musicGenreRepository.save(musicGenre);

        //sendRegistrationConfirmationEmail(vendorEntity.getUser());
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

    private void addUserGroup(User userEntity, String code){
        PrincipalGroup group = principalGroupRepository.findByCode(code);
        userEntity.setUserGroups(Set.of(group));
    }
}