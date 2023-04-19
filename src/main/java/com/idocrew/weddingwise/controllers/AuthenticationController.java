package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.entity.*;
import com.idocrew.weddingwise.repositories.CategoryRepository;
import com.idocrew.weddingwise.repositories.MusicGenreRepository;
import com.idocrew.weddingwise.repositories.UserRepository;
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
public class AuthenticationController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login_and_signup/login";
    }

}
