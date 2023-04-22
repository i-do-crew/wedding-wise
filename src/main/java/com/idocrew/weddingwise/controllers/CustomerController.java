package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.entity.Budget;
import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.User;
import com.idocrew.weddingwise.repositories.BudgetRepository;
import com.idocrew.weddingwise.repositories.CustomerRepository;
import com.idocrew.weddingwise.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class CustomerController {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final BudgetRepository budgetRepository;
    @GetMapping("/ideaboard")
    public String ideaBoard(){
        return "customer_views/idea_board";
    }
    @GetMapping("/clients/dashboard")
    public String clientProfile(@CurrentSecurityContext(expression="authentication?.name") String username, Model model){
        User user = userRepository.findByUsername(username);
        Customer customer = customerRepository.findCustomerByUser(user);
        Budget budget = budgetRepository.findBudgetByCustomer(customer);
        model.addAttribute("user", user);
        model.addAttribute("customer", customer);
        model.addAttribute("budget", budget);
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
