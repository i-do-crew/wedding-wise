package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.entity.*;
import com.idocrew.weddingwise.services.*;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@SessionAttributes({"user","customer","budget"})
public class CustomerController {

    private final UserService userService;
    private final CustomerService customerService;
    private final BudgetEntryService budgetEntryService;
    private final CustomerVendorService customerVendorService;
    private final VendorCategoryService vendorCategoryService;
    private final VendorUtility vendorUtility;


    private void refactorThisMethod(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model, HttpServletRequest request) {
        User user = userService.findByUsername(username);
        Customer customer = customerService.findCustomerByUser(user);
        List<VendorCategory> vendorCategories = vendorCategoryService.findAll();
        List<BudgetEntry> budget = budgetEntryService.findBudgetEntriesByCustomer(customer);
        Set<CustomerVendor> customerVendors = customerVendorService.findByCustomer(customer);
        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("customer", customer);
        request.getSession().setAttribute("budget", budget);
        request.getSession().setAttribute("customerVendors", customerVendors);
        request.getSession().setAttribute("categories", vendorCategories);
    }
    @GetMapping("/ideaboard")
    public String ideaBoard(@CurrentSecurityContext(expression="authentication?.name") String username, Model model, HttpServletRequest request){
        refactorThisMethod(username, model, request);
        return "customer_views/idea_board";
    }
    @GetMapping("/clients/dashboard")
    public String clientProfile(@CurrentSecurityContext(expression="authentication?.name") String username, Model model, HttpServletRequest request){
        refactorThisMethod(username, model, request);
        return "customer_views/client_profileDashboard";
    }
    @GetMapping("/budget_tracker")
    public String budgetTracker(@CurrentSecurityContext(expression="authentication?.name") String username, Model model, HttpServletRequest request){
        refactorThisMethod(username, model, request);
        return "/clients_budgetTracker";
    }
    @GetMapping("/likedVendors/toggle/{vendorId}")
    public String toggleLikedVendors(@PathVariable Long vendorId, @CurrentSecurityContext(expression = "authentication?.name") String username, Model model, HttpServletRequest request){
        refactorThisMethod(username, model, request);
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        Vendor vendor = vendorUtility.findById(vendorId);
        Optional<CustomerVendor> opt = customerVendorService.findByCustomerAndVendor(customer, vendor);
        CustomerVendor likedVendor = null;
        if(opt.isPresent()) {
            likedVendor = opt.get();
            if(!likedVendor.getLiked()){
                likedVendor.setLiked(true);
            } else{
                likedVendor.setLiked(false);
            }
        } else {
            likedVendor = new CustomerVendor();
            likedVendor.setCustomer(customer);
            likedVendor.setVendor(vendor);
            likedVendor.setLiked(true);
        }
        customerVendorService.save(likedVendor);
        Set<CustomerVendor> customerVendors = customerVendorService.findByCustomer(customer);
        request.getSession().setAttribute("customerVendors", customerVendors);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
    @GetMapping("/likedVendors")
    public String likedVendors(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model, HttpServletRequest request) {
        refactorThisMethod(username, model, request);
        return "/likedVendors";
    }
    @GetMapping("/selectedVendors/toggle/{vendorId}")
    public String addSelectedVendor(@PathVariable Long vendorId, @CurrentSecurityContext(expression = "authentication?.name") String username, Model model, HttpServletRequest request) {
        refactorThisMethod(username, model, request);
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        Vendor vendor = vendorUtility.findById(vendorId);
        Optional<CustomerVendor> opt = customerVendorService.findByCustomerAndVendor(customer, vendor);
        CustomerVendor selectedVendor = null;
        if(opt.isPresent()) {
            selectedVendor = opt.get();
            if(!selectedVendor.getSelected()){
                selectedVendor.setSelected(true);
            } else{
                selectedVendor.setSelected(false);
            }
        } else {
            selectedVendor = new CustomerVendor();
            selectedVendor.setCustomer(customer);
            selectedVendor.setVendor(vendor);
            selectedVendor.setSelected(true);
        }
        customerVendorService.save(selectedVendor);
        Set<CustomerVendor> customerVendors = customerVendorService.findByCustomer(customer);
        request.getSession().setAttribute("customerVendors", customerVendors);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}