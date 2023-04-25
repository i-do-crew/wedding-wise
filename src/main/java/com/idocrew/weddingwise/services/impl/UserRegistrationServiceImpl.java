package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.context.AccountVerificationEmailContext;
import com.idocrew.weddingwise.entity.*;
    import com.idocrew.weddingwise.exception.InvalidTokenException;
import com.idocrew.weddingwise.services.*;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

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
    private final DjsAndLiveBandsService djsAndLiveBandsService;
    private final DjsAndLiveBandsMusicGenreService djsAndLiveBandsMusicGenreService;
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
        vendorEntity = saveVendor(vendorEntity);

        switch (vendorEntity.getVendorCategory().getTitle()) {
            case "Venues" -> saveVenue(vendorEntity, vendorComposite);
            case "Photographers" -> savePhotographer(vendorEntity, vendorComposite.getPhotoFormat());
            case "Bands and DJs" -> {
                DjsAndLiveBand djOrLiveBand = saveDjOrBand(vendorEntity, vendorComposite.getDjsAndLiveBandsCategory());
                saveDjOrBandMusicGenres(djOrLiveBand, vendorComposite.getMusicGenres());
            }
            default -> {
            }
        }
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

    private DjsAndLiveBand saveDjOrBand(Vendor vendorEntity, DjsAndLiveBandsCategory category) {
        DjsAndLiveBand djOrLiveBand = new DjsAndLiveBand(vendorEntity, category);
        return djsAndLiveBandsService.saveDjsAndLiveBands(djOrLiveBand);
    }

    private void saveDjOrBandMusicGenres(DjsAndLiveBand djsOrLiveBand, Set<MusicGenre> musicGenres) {
        Set<DjsAndLiveBandsMusicGenre> set = musicGenres
                .stream()
                .map(musicGenre -> new DjsAndLiveBandsMusicGenre(djsOrLiveBand, musicGenre))
                .collect(Collectors.toSet());
        djsAndLiveBandsMusicGenreService.saveAllDjsAndLiveBandsMusicGenres(set);
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
        return userService.findByUsername(userName) != null;
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