package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.Guest;
import com.idocrew.weddingwise.entity.User;
import com.idocrew.weddingwise.services.CustomerService;
import com.idocrew.weddingwise.services.GuestListService;
import com.idocrew.weddingwise.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class GuestListController {

    private final GuestListService guestListService;
    private final UserService userService;
    private final CustomerService customerService;

    @GetMapping("/clients/guests")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String guestListManager(@CurrentSecurityContext(expression="authentication?.name") String username, Model model, HttpServletRequest request){
        System.out.println("/clients/guests");
        User user = userService.findByUsername(username);
        Customer customer = customerService.findCustomerByUser(user);
        model.addAttribute("guest", new Guest());
        model.addAttribute("guestList", guestListService.findByCustomer(customer));
        model.addAttribute("user", user);
        model.addAttribute("customer", customer);
        return "/guest_listManager";
    }

    @PostMapping("/clients/guests/add")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String addGuest(Model model, HttpServletRequest request){
        System.out.println("/clients/add/guest");
        return "/guest_listManager";
    }

    @PostMapping("/clients/guests/update/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String updateGuest(@PathVariable long id, Model model, HttpServletRequest request){
        System.out.println("/clients/guest/update/{id}: " + id);
        return "/guest_listManager";
    }

    @PostMapping("/clients/guests/remove/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String deleteGuest(@PathVariable long id, Model model, HttpServletRequest request){
        System.out.println("/client/guest/remove/{id}: " + id);
        return "/guest_listManager";
    }

}