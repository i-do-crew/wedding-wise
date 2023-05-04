package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.context.AccountVerificationEmailContext;
import com.idocrew.weddingwise.entity.*;
import com.idocrew.weddingwise.exception.InvalidTokenException;
import com.idocrew.weddingwise.services.*;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.idocrew.weddingwise.enums.UserType.CUSTOMER;
import static com.idocrew.weddingwise.enums.UserType.VENDOR;

@RequiredArgsConstructor
@Service("userRegistrationService")
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserService userService;
    private final VendorUtility vendorUtility;
    private final CustomerService customerService;
    private final MusicVendorService musicVendorService;
    private final MusicVendorGenreService musicVendorGenreService;
    private final EmailService emailService;
    private final PrincipalGroupService principalGroupService;
    private final VendorPhotoFormatService vendorPhotoFormatService;
    private final VenueService venueService;
    private final SecureTokenService secureTokenService;
    private final PasswordEncoder passwordEncoder;
    @Value("${site.base.url.https}")
    private String baseURL;

    @Override
    @Transactional
    public void register(Customer customer) throws DuplicateKeyException {
        if(checkIfUserExist(customer.getUser().getEmail())){
            throw new DuplicateKeyException("Customer already exists for this email");
        }

        User userEntity = saveUser(customer.getUser(), CUSTOMER.getCode());
        Customer customerEntity = new Customer();
        BeanUtils.copyProperties(customer, customerEntity);
        customerEntity.setUser(userEntity);
        saveCustomer(customerEntity);
        sendRegistrationConfirmationEmail(userEntity);
    }

    private void saveCustomer(Customer customer) {
        customerService.saveCustomer(customer);
    }

    @Override
    @Transactional
    public void register(VendorComposite vendorComposite) {
        if(checkIfUserExist(vendorComposite.getUser().getEmail())){
            throw new DuplicateKeyException("Vendor already exists for this email");
        }

        User userEntity = saveUser(vendorComposite.getUser(), VENDOR.getCode());
        Vendor vendorEntity = new Vendor();
        BeanUtils.copyProperties(vendorComposite.getVendor(), vendorEntity);
        vendorEntity.setUser(userEntity);
        vendorEntity = vendorUtility.saveVendor(vendorEntity);
        vendorUtility.savedVendorAttributes(vendorComposite, vendorEntity);
        sendRegistrationConfirmationEmail(userEntity);
    }

    @Override
    public boolean verifyUser(String token) throws InvalidTokenException {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if(Objects.isNull(secureToken) || !StringUtils.equals(token, secureToken.getToken()) || secureToken.isExpired()){
            throw new InvalidTokenException("Token is not valid");
        }
        User userEntity = userService.findUserById(secureToken.getUser().getId());
        if(Objects.isNull(userEntity)){
            return false;
        }
        userEntity.setAccountVerified(true);
        userEntity.setLoginDisabled(false);
        userService.saveUser(userEntity); // let's save user details

        // we don't need invalid password now
        secureTokenService.removeToken(secureToken);
        return true;
    }

    private MusicVendor saveMusicVendor(Vendor vendorEntity, MusicVendorCategory category) {
        MusicVendor musicVendor = new MusicVendor(vendorEntity, category);
        return musicVendorService.saveMusicVendor(musicVendor);
    }

    private void saveMusicVendorMusicGenres(MusicVendor musicVendor, Set<MusicGenre> musicGenres) {
        Set<MusicVendorGenre> set = musicGenres
                .stream()
                .map(musicGenre -> new MusicVendorGenre(musicVendor, musicGenre))
                .collect(Collectors.toSet());
        musicVendorGenreService.saveAllMusicVendorMusicGenres(set);
    }

    private void savePhotographer(Vendor vendorEntity, PhotoFormat photoFormat) {
        VendorPhotoFormat vendorPhotoFormatEntity = new VendorPhotoFormat();
        vendorPhotoFormatEntity.setVendor(vendorEntity);
        vendorPhotoFormatEntity.setPhotoFormat(photoFormat);
        vendorPhotoFormatService.saveVendorPhotoFormat(vendorPhotoFormatEntity);
    }

    private void saveVenue(Vendor vendorEntity, VendorComposite vendorComposite) {
        Venue venueEntity = new Venue();
        BeanUtils.copyProperties(vendorComposite.getVenue(), venueEntity);
        venueEntity.setVendor(vendorEntity);
        venueService.saveVenue(venueEntity);
    }

    private Vendor saveVendor(Vendor vendor) {
        Vendor vendorEntity = new Vendor();
        BeanUtils.copyProperties(vendor, vendorEntity);
        return vendorUtility.saveVendor(vendorEntity);
    }

    private User saveUser(User user, String code) {
        User userEntity = new User();
        BeanUtils.copyProperties(user, userEntity);
        String hash = passwordEncoder.encode(user.getPassword());
        userEntity.setPassword(hash);
        userEntity.setUsername(user.getEmail());
        userEntity.addGroup(getUserGroup(code));
        userEntity = userService.saveUser(userEntity);
        return userEntity;
    }

    private boolean checkIfUserExist(String userName) {
        User userEntity = null;
        try {
            userEntity = userService.findByUsername(userName);
        } catch (UsernameNotFoundException e) {
            // we expect to get a username not found exception
        }
        return userEntity != null;
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

    private PrincipalGroup getUserGroup(String role){
        return principalGroupService.findByCode(role);
    }
}