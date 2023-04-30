package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.Guest;
import com.idocrew.weddingwise.entity.User;
import com.idocrew.weddingwise.services.CustomerService;
import com.idocrew.weddingwise.services.GuestListService;
import com.idocrew.weddingwise.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class GuestListController {

    private final GuestListService guestListService;
    private final UserService userService;
    private final CustomerService customerService;
    @Value("#{'${us.states}'.split(',')}")
    private final String[] states;

    @GetMapping("/clients/guests")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String guestListManager(@CurrentSecurityContext(expression="authentication?.name") String username,
                                   HttpServletRequest request, Model model){
        System.out.println("/clients/guests");
        User user = userService.findByUsername(username);
        Customer customer = customerService.findCustomerByUser(user);
        List<Guest> guestList = guestListService.findByCustomer(customer);
        model.addAttribute("options", states);
        model.addAttribute("guest", new Guest());
        model.addAttribute("guestList", guestList);
        model.addAttribute("user", user);
        request.getSession().setAttribute("customer", customer);
        return "/customer_views/guest-list";
    }
    @PostMapping("/clients/guests/add")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String addGuest(@Valid @ModelAttribute("guest") Guest guest, @SessionAttribute("customer") Customer customer,
                           BindingResult result) {
        if (result.hasErrors()) {
            return "/customer_views/guest-list";
        }
        guest.setCustomer(customer);
        guestListService.save(guest);
        return "/customer_views/guest-list";
    }

    @PostMapping("/clients/guests/update/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String updateGuest(@ModelAttribute("guest") Guest guest, @PathVariable long id){
        guestListService.save(guest);
        return "/customer_views/guest-list";
    }

    @PostMapping("/clients/guests/remove/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String deleteGuest(@PathVariable long id){
        guestListService.delete(guestListService.findById(id));
        return "/customer_views/guest-list";
    }

}