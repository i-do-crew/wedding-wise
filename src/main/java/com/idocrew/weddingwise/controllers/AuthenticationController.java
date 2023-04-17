package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.entity.*;
import com.idocrew.weddingwise.repositories.CategoryRepository;
import com.idocrew.weddingwise.repositories.GenreRepository;
import com.idocrew.weddingwise.repositories.UserRepository;
import com.idocrew.weddingwise.services.UserRegistrationService;
import com.idocrew.weddingwise.services.impl.UserRegistrationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final GenreRepository genreRepository;
    private final UserRegistrationService userRegistrationService;
    @Value("#{'${us.states}'.split(',')}")
    private final String[] states;
    @GetMapping("/login")
    public String showLoginForm() {
        return "login_and_signup/login";
    }
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
        model.addAttribute("musicGenres", genreRepository.findAll());
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
