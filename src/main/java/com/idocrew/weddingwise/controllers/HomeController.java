package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class HomeController {


    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        model.addAttribute("user", user);
        model.addAttribute("customer", customer);
        return "index";
    }

    @GetMapping("/aboutus")
    public String aboutUs(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        model.addAttribute("user", user);
        model.addAttribute("customer", customer);
        return "about_us";
    }

    @GetMapping("/info/budget")
    public String visitorBudgetTracker() {
        return "visitor_views/budgetTracker_learnMore";
    }

    @GetMapping("/info/guests")
    public String guestListManager() {
        return "visitor_views/guestList_learnMore";
    }

    @GetMapping("/info/ideas")
    public String visitorIdeaBoard() {
        return "visitor_views/ideaBoard_learnMore";
    }
    @GetMapping("/info/vendors")
    public String visitorVendors() {
        return "visitor_views/vendors_learn_more";
    }






}
