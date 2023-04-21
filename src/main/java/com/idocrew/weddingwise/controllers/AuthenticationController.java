package com.idocrew.weddingwise.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login_and_signup/login";
    }

}
