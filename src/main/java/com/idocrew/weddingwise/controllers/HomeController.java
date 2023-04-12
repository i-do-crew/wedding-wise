package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.entity.Vendor;
import com.idocrew.weddingwise.repositories.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final VendorRepository vendorDao;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/visitor/budgettracker")
    public String visitorBudgetTracker() {
        return "visitor_budget_tracker";
    }

    @GetMapping("/visitor/guestlistmanager")
    public String guestListManager() {
        return "visitor_guest_list_manager";
    }

    @GetMapping("/visitor/ideaboard")
    public String visitorIdeaBoard() {
        return "visitor_idea_board";
    }

    @GetMapping("/aboutus")
    public String aboutUs() {
        return "about_us";
    }

    @GetMapping("/client/login")
    public String clientLogin() {
        return "client_login";
    }

    @GetMapping("/client/registration")
    public String clientRegistration() {
        return "client_registration";
<<<<<<< HEAD
=======
    }

//    @PostMapping("/client/registration")
//    public String clientRegistrationPost(){
//        return "redirect:/verification";
//    }

    @GetMapping("/vendor/registration")
    public String vendorRegistration(){
        return "vendor_registration";
    }

//    @PostMapping("/vendor/registration")
//    public String vendorRegistrationPost(){
//        return "redirect:/verification";
//    }

    @GetMapping("/verification")
    public String emailVerification(){
        return "email_verification";
    }

    @GetMapping("/vendors")
    public String vendorCategories(Model model){
        List<Vendor> vendors = vendorDao.findAll();
        model.addAttribute("vendors",vendors);
        return "all_vendorCategories";
>>>>>>> f37fa39778499240fac6fbeb4e8eab8af39f8ec5
    }
}
