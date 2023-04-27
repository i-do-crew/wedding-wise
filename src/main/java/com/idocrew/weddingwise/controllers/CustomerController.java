package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.entity.*;
import com.idocrew.weddingwise.services.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;
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
    @GetMapping("/guest_listManager")
    public String guestListManager(@CurrentSecurityContext(expression="authentication?.name") String username, Model model, HttpServletRequest request){
        refactorThisMethod(username, model, request);
        return "/guest_listManager";
    }
    @GetMapping("/budget_tracker")
    public String budgetTracker(@CurrentSecurityContext(expression="authentication?.name") String username, Model model, HttpServletRequest request){
        refactorThisMethod(username, model, request);
        return "/clients_budgetTracker";
    }
    @GetMapping("/likedVendors")
    public String likedVendors(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model, HttpServletRequest request) {
        refactorThisMethod(username, model, request);
        return "/likedVendors";
    }
    @GetMapping("/likedVendors/add/{vendorId}")
    public String addLikedVendor(@PathVariable Long vendorId, @CurrentSecurityContext(expression = "authentication?.name") String username, Model model, HttpServletRequest request) {
        refactorThisMethod(username, model, request);
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        Vendor vendor = vendorUtility.findById(vendorId);
        CustomerVendor likedVendor = new CustomerVendor();
        likedVendor.setCustomer(customer);
        likedVendor.setVendor(vendor);
        likedVendor.setLiked(true);
        customerVendorService.save(likedVendor);
        return "/likedVendors";
    }
    @GetMapping("/likedVendors/remove/{vendorId}")
    public String removeLikedVendor(@PathVariable Long vendorId, @CurrentSecurityContext(expression = "authentication?.name") String username, Model model, HttpServletRequest request) {
        refactorThisMethod(username, model, request);
        CustomerVendor likedVendor = customerVendorService.findById(vendorId);
        likedVendor.setLiked(false);
        customerVendorService.save(likedVendor);

        return "/likedVendors";
    }
}