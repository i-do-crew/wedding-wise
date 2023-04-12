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
        return "visitor_views/visitor_budget_tracker";
    }

    @GetMapping("/visitor/guestlistmanager")
    public String guestListManager() {
        return "visitor_views/visitor_guest_list_manager";
    }

    @GetMapping("/visitor/ideaboard")
    public String visitorIdeaBoard() {
        return "visitor_views/visitor_idea_board";
    }

    @GetMapping("/aboutus")
    public String aboutUs() {
        return "about_us";
    }

    @GetMapping("/login")
    public String clientLogin() {
        return "login_and_signup/login";
    }

    @GetMapping("/client/registration")
    public String clientRegistration() {
        return "login_and_signup/client_registration";
    }

//    @PostMapping("/client/registration")
//    public String clientRegistrationPost(){
//        return "redirect:/verification";
//    }

    @GetMapping("/vendor/registration")
    public String vendorRegistration(){
        return "login_and_signup/vendor_registration";
    }

//    @PostMapping("/vendor/registration")
//    public String vendorRegistrationPost(){
//        return "redirect:/verification";
//    }

    @GetMapping("/verification")
    public String emailVerification(){
        return "login_and_signup/email_verification";
    }

    @GetMapping("/vendors")
    public String vendorCategories(Model model){
        List<Vendor> vendors = vendorDao.findAll();
        model.addAttribute("vendors",vendors);
        return "vendors/all_vendorCategories";
    }

    @GetMapping("/ideaboard")
    public String ideaBoard(){
        return "customer_views/idea_board";
    }

    @GetMapping("/vendor/profile")
    public String vendorProfile(){
        return "vendor_views/vendor_profile";
    }
}
