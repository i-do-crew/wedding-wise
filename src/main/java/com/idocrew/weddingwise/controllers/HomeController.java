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


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/info/budget")
    public String visitorBudgetTracker() {
        return "visitor_views/visitor_budget_tracker";
    }

    @GetMapping("/info/guests")
    public String guestListManager() {
        return "visitor_views/visitor_guest_list_manager";
    }

    @GetMapping("/info/ideas")
    public String visitorIdeaBoard() {
        return "visitor_views/visitor_idea_board";
    }
    @GetMapping("/info/vendors")
    public String visitorVendors() {
        return "visitor_views/vendors_learn_more";
    }
    @GetMapping("/aboutus")
    public String aboutUs() {
        return "about_us";
    }





}
