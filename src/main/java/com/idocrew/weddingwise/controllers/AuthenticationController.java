package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.entity.User;
import com.idocrew.weddingwise.repositories.UserRepository;
import com.idocrew.weddingwise.services.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserRepository userRepository;
    private final UserRegistrationService userRegistrationService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login_and_signup/login";
    }
    @GetMapping("/client/registration")
    public String clientRegistration(Model model) {
        model.addAttribute("user", new User());
        return "login_and_signup/client_registration";
    }
    @PostMapping("/client/registration")
    public String clientRegistrationPost(@ModelAttribute User user){
        userRegistrationService.register(user);
        return "redirect:/verification";
    }
    @GetMapping("/vendor/registration")
    public String vendorRegistration(){
        return "login_and_signup/vendor_registration";
    }
    @PostMapping("/vendor/registration")
    public String vendorRegistrationPost(){
        return "redirect:/verification";
    }

    @GetMapping("/verification")
    public String emailVerification(){
        return "login_and_signup/email_verification";
    }

}
