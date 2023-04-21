package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.entity.*;
import com.idocrew.weddingwise.repositories.*;
import com.idocrew.weddingwise.services.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;

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
        VendorComposite vendorComposite = VendorComposite.builder()
                .vendor(new Vendor())
                .musicGenres(new HashSet<>())
                .photoFormat(new PhotoFormat())
                .djsAndLiveBandsCategory(new DjsAndLiveBandsCategory())
                .venue(new Venue())
                .build();
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
    @GetMapping("/verification")
    public String emailVerification(){
        return "login_and_signup/email_verification";
    }
}
