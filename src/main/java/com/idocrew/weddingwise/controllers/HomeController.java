package com.idocrew.weddingwise.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
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
}
