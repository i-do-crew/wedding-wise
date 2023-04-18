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

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final CategoryRepository categoryRepository;
    private final MusicGenreRepository musicGenreRepository;
    private final UserRegistrationService userRegistrationService;
    private final PhotoFormatRepository photoFormatRepository;
    private final MusicTypeRepository musicTypeRepository;
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
        model.addAttribute("options", states);
        model.addAttribute("vendor", new Vendor());
        model.addAttribute("vendorCategories", categoryRepository.findAll());
        model.addAttribute("genre", new MusicGenre());
        model.addAttribute("musicGenres", musicGenreRepository.findAll());
        model.addAttribute("photoFormat", new PhotoFormat());
        model.addAttribute("photoFormats", photoFormatRepository.findAll());
        model.addAttribute("musicType", new MusicType());
        model.addAttribute("musicTypes", musicTypeRepository.findAll());
        return "login_and_signup/vendor_registration";
    }
    @PostMapping("/vendor/registration")
    public String vendorRegistrationPost(@ModelAttribute Vendor vendor){
        userRegistrationService.register(vendor);
        return "redirect:/verification";
    }
    @GetMapping("/verification")
    public String emailVerification(){
        return "login_and_signup/email_verification";
    }
}
