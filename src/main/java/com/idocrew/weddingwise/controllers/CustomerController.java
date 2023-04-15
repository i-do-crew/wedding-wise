package com.idocrew.weddingwise.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {
    @GetMapping("/ideaboard")
    public String ideaBoard(){
        return "customer_views/idea_board";
    }
    @GetMapping("/clients/dashboard")
    public String clientProfile(){
        return "customer_views/client_profileDashboard";
    }
    @GetMapping("/guest_listManager")
    public String guestListManager(){
        return "/guest_listManager";
    }
    @GetMapping("/budget_tracker")
    public String budgetTracker(){
        return "/clients_budgetTracker";
    }
}
