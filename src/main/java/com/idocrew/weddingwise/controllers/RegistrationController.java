package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.VendorComposite;
import com.idocrew.weddingwise.exception.InvalidTokenException;
import com.idocrew.weddingwise.repositories.DjsAndLiveBandsCategoryRepository;
import com.idocrew.weddingwise.repositories.MusicGenreRepository;
import com.idocrew.weddingwise.repositories.PhotoFormatRepository;
import com.idocrew.weddingwise.repositories.VendorCategoryRepository;
import com.idocrew.weddingwise.services.UserRegistrationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final VendorCategoryRepository vendorCategoryRepository;
    private final MusicGenreRepository musicGenreRepository;
    private final UserRegistrationService userRegistrationService;
    private final PhotoFormatRepository photoFormatRepository;
    private final DjsAndLiveBandsCategoryRepository djsAndLiveBandsCategoryRepository;
    @Value("#{'${us.states}'.split(',')}")
    private final String[] states;
    private static final String REDIRECT_LOGIN = "redirect:/login";
    private UserRegistrationService registrationService;
    private MessageSource messageSource;
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @GetMapping("/client/registration")
    public String clientRegistration(Model model) {
        model.addAttribute("options", states);
        model.addAttribute("customer", new Customer());
        return "login_and_signup/client_registration";
    }
    @PostMapping("/client/registration")
    public String clientRegistrationPost(@ModelAttribute Customer customer){
        userRegistrationService.register(customer);
        return "redirect:/verification";
    }
    @GetMapping("/vendor/registration")
    public String vendorRegistration(Model model){
        model.addAttribute("options", states);
        model.addAttribute("vendorComposite", new VendorComposite());
        model.addAttribute("vendorCategories", vendorCategoryRepository.findAll());
        model.addAttribute("musicGenres", musicGenreRepository.findAll());
        model.addAttribute("photoFormats", photoFormatRepository.findAll());
        model.addAttribute("musicTypes", djsAndLiveBandsCategoryRepository.findAll());
        return "login_and_signup/vendor_registration";
    }
    @PostMapping("/vendor/registration")
    public String vendorRegistrationPost(@ModelAttribute("vendorComposite") VendorComposite vendorComposite){
        userRegistrationService.register(vendorComposite);
        return "redirect:/verification";
    }
    @GetMapping("register/verify")
    public String emailVerification(@RequestParam(required = false) String token, final Model model, RedirectAttributes redirAttr) throws ServletException, IOException {
        if(StringUtils.isEmpty(token)){
            redirAttr.addFlashAttribute("tokenError", messageSource.getMessage("user.registration.verification.missing.token", null,LocaleContextHolder.getLocale()));
            return REDIRECT_LOGIN;
            }
        try {
            userRegistrationService.verifyUser(token);
        } catch (InvalidTokenException e) {
            redirAttr.addFlashAttribute("tokenError", messageSource.getMessage("user.registration.verification.invalid.token", null,LocaleContextHolder.getLocale()));
            return REDIRECT_LOGIN;
        }
        return REDIRECT_LOGIN;
    }
}
