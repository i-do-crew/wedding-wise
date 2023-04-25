package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.VendorComposite;
import com.idocrew.weddingwise.exception.InvalidTokenException;
import com.idocrew.weddingwise.services.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserRegistrationService userRegistrationService;
    private final VendorCategoryService vendorCategoryService;
    private final MusicGenreService musicGenreService;
    private final PhotoFormatService photoFormatService;
    private final DjsAndLiveBandsCategoryService djsAndLiveBandsCategoryService;
    @Value("#{'${us.states}'.split(',')}")
    private final String[] states;
    private static final String REDIRECT_LOGIN = "redirect:/login";
    private final MessageSource messageSource;

    @GetMapping("/client/registration")
    public String clientRegistration(Model model) {
        model.addAttribute("options", states);
        model.addAttribute("customer", new Customer());
        return "login_and_signup/client_registration";
    }
    @PostMapping("/client/registration")
    public String clientRegistrationPost(@ModelAttribute Customer customer){
        userRegistrationService.register(customer);
        return REDIRECT_LOGIN;
    }
    @GetMapping("/vendor/registration")
    public String vendorRegistration(Model model){
        model.addAttribute("options", states);
        model.addAttribute("vendorComposite", new VendorComposite());
        model.addAttribute("vendorCategories", vendorCategoryService.findAll());
        model.addAttribute("musicGenres", musicGenreService.findAll());
        model.addAttribute("photoFormats", photoFormatService.findAll());
        model.addAttribute("musicTypes", djsAndLiveBandsCategoryService.findAll());
        return "login_and_signup/vendor_registration";
    }
    @PostMapping("/vendor/registration")
    public String vendorRegistrationPost(@ModelAttribute("vendorComposite") VendorComposite vendorComposite){
        userRegistrationService.register(vendorComposite);
        return REDIRECT_LOGIN;
    }
    @GetMapping("register/verify")
    public String emailVerification(@RequestParam(required = false) String token, RedirectAttributes redirAttr) {
        if(StringUtils.isEmpty(token)){
            redirAttr.addFlashAttribute("tokenError", messageSource.getMessage("user.registration.verification.missing.token", null, LocaleContextHolder.getLocale()));
            return REDIRECT_LOGIN;
            }
        try {
            userRegistrationService.verifyUser(token);
        } catch (InvalidTokenException e) {
            redirAttr.addFlashAttribute("tokenError", messageSource.getMessage("user.registration.verification.invalid.token", null, LocaleContextHolder.getLocale()));
            return REDIRECT_LOGIN;
        }
        return REDIRECT_LOGIN;
    }

}
