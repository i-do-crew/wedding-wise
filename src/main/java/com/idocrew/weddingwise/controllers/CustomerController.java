package com.idocrew.weddingwise.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class CustomerController {
    @GetMapping("/ideaboard")
    public String ideaBoard(){
        return "customer_views/idea_board";
    }
    @GetMapping("/clients/dashboard")
    public String clientProfile(){
        return "customer_views/client_profileDashboard";
    }
}
