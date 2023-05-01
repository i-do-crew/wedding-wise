package com.idocrew.weddingwise.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.Guest;
import com.idocrew.weddingwise.entity.User;
import com.idocrew.weddingwise.enums.InviteResponseType;
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
        int invited = 0;
        int attending = 0;
        int declined = 0;
        for (Guest guest : guestList) {
            if (guest.isAttending()) {
                attending += guest.getPlusOne() ? 2 : 1;
            } else if (guest.isAttending()) {
                attending++;
            } else if (guest.isDeclined()) {
                declined++;
            } else {
                invited++;
            }
        }
        model.addAttribute("invited", invited);
        model.addAttribute("attending", attending);
        model.addAttribute("declined", declined);
        model.addAttribute("states", states);
        model.addAttribute("guest", new Guest());
        model.addAttribute("guestList", guestList);
        model.addAttribute("user", user);
        request.getSession().setAttribute("customer", customer);
        return "/customer_views/guest-list";
    }
    @PostMapping("/clients/guests/add")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String addGuest(@Valid @ModelAttribute("guest") Guest guest, @SessionAttribute("customer") Customer customer,
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/customer_views/guest-list";
        }
        guest.setCustomer(customer);
        guestListService.save(guest);
        return "redirect:/clients/guests";
    }

    @PostMapping("/clients/guests/update/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String updateGuest(@ModelAttribute("guest") Guest guest, @SessionAttribute("customer") Customer customer,
                              BindingResult result, @PathVariable long id){
        if (result.hasErrors()) {
            return "/customer_views/guest-list";
        }
        guest.setCustomer(customer);
        guestListService.save(guest);
        return "redirect:/clients/guests";
    }

    @PostMapping("/clients/guests/remove/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String deleteGuest(@PathVariable long id){
        Guest guest = guestListService.findById(id);
        guestListService.delete(guest);
        return "redirect:/clients/guests";
    }

    @PostMapping("/clients/guests/rsvp/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String rsvpGuest(@PathVariable long id) {
        Guest guest = guestListService.findById(id);
        guest.setRsvp(InviteResponseType.RSVP.getCode());
        guestListService.save(guest);
        return "redirect:/clients/guests";
    }

    @PostMapping("/clients/guests/decline/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String declineGuest(@PathVariable long id) {
        Guest guest = guestListService.findById(id);
        guest.setRsvp(InviteResponseType.DECLINED.getCode());
        guestListService.save(guest);
        return "redirect:/clients/guests";
    }

}