package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.entity.*;
import com.idocrew.weddingwise.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Controller
@SessionAttributes({"customer", "budgetEntries", "customerVendors", "categories"})
public class CustomerController {

    private final UserService userService;
    private final CustomerService customerService;
    private final CustomerVendorService customerVendorService;
    private final VendorCategoryService vendorCategoryService;
    private final VendorUtility vendorUtility;
    private final BudgetEntryService budgetEntryService;
    @Value("#{'${us.states}'.split(',')}")
    private final String[] states;

    private void setSessionAttributes(String username, Model model, HttpServletRequest request) {
        Customer customer = customerService.findCustomerByUser(userService.findByUsername(username));
        HttpSession session =  request.getSession();
        session.setAttribute("customer", customer);
        session.setAttribute("budgetEntries", budgetEntryService.findBudgetEntriesByCustomer(customer));
        session.setAttribute("customerVendors", customerVendorService.findByCustomer(customer));
        session.setAttribute("categories", vendorCategoryService.findAll());
        model.addAttribute("options", states);
    }

    @GetMapping("/ideaboard")
    public String ideaBoard(@CurrentSecurityContext(expression="authentication?.name") String username, Model model, HttpServletRequest request){
        setSessionAttributes(username, model, request);
        User user = (User) request.getSession().getAttribute("user");
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        model.addAttribute("user", user);
        model.addAttribute("customer", customer);
        return "customer_views/idea_board";
    }

    @GetMapping("/clients/dashboard")
    public String clientProfile(@CurrentSecurityContext(expression="authentication?.name") String username, Model model, HttpServletRequest request){
        setSessionAttributes(username, model, request);
        User user = (User) request.getSession().getAttribute("user");
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        model.addAttribute("user", user);
        model.addAttribute("customer", customer);
        return "customer_views/client_profileDashboard";
    }

    @GetMapping("/likedVendors/toggle/{vendorId}")
    public String toggleLikedVendors(@PathVariable Long vendorId, @CurrentSecurityContext(expression = "authentication?.name") String username, Model model, HttpServletRequest request){
        setSessionAttributes(username, model, request);
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        Vendor vendor = vendorUtility.findById(vendorId);
        CustomerVendor likedVendor = getLikedVendor(customer, vendor);
        likedVendor.setLiked(!likedVendor.getLiked());
        customerVendorService.save(likedVendor);
        Set<CustomerVendor> customerVendors = customerVendorService.findByCustomer(customer);
        request.getSession().setAttribute("customerVendors", customerVendors);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    private CustomerVendor getLikedVendor(Customer customer, Vendor vendor) {
        Optional<CustomerVendor> customerVendor = customerVendorService.findByCustomerAndVendor(customer, vendor);
        CustomerVendor likedVendor = customerVendor.orElseGet(() -> {
            CustomerVendor newCustomerVendor = new CustomerVendor();
            newCustomerVendor.setCustomer(customer);
            newCustomerVendor.setVendor(vendor);
            newCustomerVendor.setLiked(true);
            return newCustomerVendor;
        });
        return likedVendor;
    }

    @GetMapping("/likedVendors")
    public String likedVendors(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model, HttpServletRequest request) {
        setSessionAttributes(username, model, request);
        User user = (User) request.getSession().getAttribute("user");
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        model.addAttribute("user", user);
        model.addAttribute("customer", customer);
        return "/likedVendors";
    }

    @GetMapping("/selectedVendors/toggle/{vendorId}")
    public String toggleSelectedVendor(@PathVariable Long vendorId, @CurrentSecurityContext(expression = "authentication?.name") String username, Model model, HttpServletRequest request) {
        setSessionAttributes(username, model, request);
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        Vendor vendor = vendorUtility.findById(vendorId);
        Optional<CustomerVendor> customerVendor = customerVendorService.findByCustomerAndVendor(customer, vendor);

        if (!customerVendor.isPresent()) {
            CustomerVendor newCustomerVendor = new CustomerVendor();
            newCustomerVendor.setCustomer(customer);
            newCustomerVendor.setVendor(vendor);
            newCustomerVendor.setSelected(true);
            customerVendorService.save(newCustomerVendor);
            customerVendor = Optional.of(newCustomerVendor);
        }

        CustomerVendor selectedVendor = customerVendor.get();
        List<BudgetEntry> budgetEntries = budgetEntryService.findBudgetEntriesByCustomer(customer);

        if (!selectedVendor.getSelected()) {
            selectedVendor.setSelected(true);
            BudgetEntry budgetEntry = budgetEntryService.createBudgetEntry(customer, vendor);
            budgetEntries.add(budgetEntry);
        } else {
            selectedVendor.setSelected(false);
            budgetEntries.removeIf(budgetEntry -> budgetEntry.getVendor().equals(vendor));
            budgetEntryService.deleteByCustomerAndVendor(customer, vendor);
        }

        budgetEntryService.save(budgetEntries);
        Set<CustomerVendor> customerVendors = customerVendorService.findByCustomer(customer);
        request.getSession().setAttribute("customerVendors", customerVendors);
        request.getSession().setAttribute("budgetEntries", budgetEntries);
        model.addAttribute("selectedVendor", selectedVendor);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping("/customer/profile/edit")
    @Transactional
    public String customerProfileEditPost(@ModelAttribute("customer") Customer customer, HttpServletRequest request, Model model) {
        userService.editUserProfile(request, customer.getUser());
        customerService.saveCustomer(customer);
        return "customer_views/client_profileDashboard";
    }
}