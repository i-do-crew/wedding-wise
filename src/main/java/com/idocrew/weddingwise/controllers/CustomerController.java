package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.entity.*;
import com.idocrew.weddingwise.services.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Controller
@SessionAttributes({"user","customer", "budgetEntries"})
public class CustomerController {

    private final UserService userService;
    private final CustomerService customerService;
    private final CustomerVendorService customerVendorService;
    private final VendorCategoryService vendorCategoryService;
    private final VendorUtility vendorUtility;
    private final BudgetEntryService budgetEntryService;
    @Value("#{'${us.states}'.split(',')}")
    private final String[] states;


    private void refactorThisMethod(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model, HttpServletRequest request) {
        User user = userService.findByUsername(username);
        Customer customer = customerService.findCustomerByUser(user);
        List<VendorCategory> vendorCategories = vendorCategoryService.findAll();
        List<BudgetEntry> budgetEntries = budgetEntryService.findBudgetEntriesByCustomer(customer);
        Set<CustomerVendor> customerVendors = customerVendorService.findByCustomer(customer);
        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("customer", customer);
        request.getSession().setAttribute("budgetEntries", budgetEntries);
        request.getSession().setAttribute("customerVendors", customerVendors);
        request.getSession().setAttribute("categories", vendorCategories);
        model.addAttribute("options", states);
    }
    @GetMapping("/ideaboard")
    public String ideaBoard(@CurrentSecurityContext(expression="authentication?.name") String username, Model model, HttpServletRequest request){
        refactorThisMethod(username, model, request);
        return "customer_views/idea_board";
    }
    @GetMapping("/clients/dashboard")
    public String clientProfile(@CurrentSecurityContext(expression="authentication?.name") String username, Model model, HttpServletRequest request){
        refactorThisMethod(username, model, request);
        User user = (User) request.getSession().getAttribute("user");
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        model.addAttribute("user", user);
        model.addAttribute("customer", customer);
        return "customer_views/client_profileDashboard";
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
        User user = (User) request.getSession().getAttribute("user");
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        model.addAttribute("user", user);
        model.addAttribute("customer", customer);
        return "/likedVendors";
    }
    @GetMapping("/selectedVendors/toggle/{vendorId}")
    public String toggleSelectedVendor(@PathVariable Long vendorId, @CurrentSecurityContext(expression = "authentication?.name") String username, Model model, HttpServletRequest request) {
        refactorThisMethod(username, model, request);
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        Vendor vendor = vendorUtility.findById(vendorId);
        List<BudgetEntry> budgetEntries = (List<BudgetEntry>) request.getSession().getAttribute("budgetEntries");
        Optional<CustomerVendor> opt = customerVendorService.findByCustomerAndVendor(customer, vendor);
        CustomerVendor selectedVendor = null;
        BudgetEntry budgetEntry = budgetEntryService.createBudgetEntry(customer, vendor);
        if(opt.isPresent()) {
            selectedVendor = opt.get();
            if(!selectedVendor.getSelected()){
                selectedVendor.setSelected(true);
                budgetEntries.add(budgetEntry);
            } else{
                selectedVendor.setSelected(false);
                budgetEntries.remove(budgetEntry);
                budgetEntryService.delete(budgetEntry);
            }
        } else {
            selectedVendor = new CustomerVendor();
            selectedVendor.setCustomer(customer);
            selectedVendor.setVendor(vendor);
            selectedVendor.setSelected(true);
            budgetEntries.add(budgetEntry);
        }
        budgetEntryService.save(budgetEntries);
        budgetEntries = budgetEntryService.findBudgetEntriesByCustomer(customer);
        request.getSession().setAttribute("budgetEntries", budgetEntries);
        customerVendorService.save(selectedVendor);
        Set<CustomerVendor> customerVendors = customerVendorService.findByCustomer(customer);
        request.getSession().setAttribute("customerVendors", customerVendors);
        String referer = request.getHeader("Referer");
        model.addAttribute("selectedVendor", selectedVendor); // add selectedVendor to model
        return "redirect:" + referer;
    }
    @PostMapping("/customer/profile/edit")
    @Transactional
    public String customerProfileEditPost(@ModelAttribute("customer") Customer customer, HttpServletRequest request, Model model) {
        User userTemp = customer.getUser();
        User userEntity = (User) request.getSession().getAttribute("user");
        userEntity.setEmail(userTemp.getEmail());
        userEntity.setFirstName(userTemp.getFirstName());
        userEntity.setLastName(userTemp.getLastName());
        userEntity.setCity(userTemp.getCity());
        userEntity.setState(userTemp.getState());
        model.addAttribute("options", states);
        userService.saveUser(userEntity);
        customerService.saveCustomer(customer);

        return "customer_views/client_profileDashboard";
    }
}