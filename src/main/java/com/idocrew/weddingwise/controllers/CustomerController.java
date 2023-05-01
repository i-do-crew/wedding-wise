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
    private final BudgetEntryService budgetEntryService;
    private final CustomerVendorService customerVendorService;
    private final VendorCategoryService vendorCategoryService;
    private final VendorUtility vendorUtility;
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
    @GetMapping("/budget_tracker")
    public String budgetTracker(@CurrentSecurityContext(expression="authentication?.name") String username, Model model, HttpServletRequest request){
        refactorThisMethod(username, model, request);
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        model.addAttribute("customer", customer);
//        model.addAttribute("vendor", vendor);
        BudgetEntry budgetEntry = new BudgetEntry();
        model.addAttribute("budgetEntry", budgetEntry);
        List<BudgetEntry> budgetEntries = (List<BudgetEntry>) request.getSession().getAttribute("budgetEntries");
        BigDecimal newBalance = customer.getBudget();
        for (int i = 0; i < budgetEntries.size(); i++) {
            newBalance = newBalance.subtract(budgetEntries.get(i).getAmount());
        }
        model.addAttribute("currentBalance", newBalance);
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
        BudgetEntry budgetEntry = createBudgetEntry(customer, vendor);
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
    @GetMapping("/budget_tracker/edit/{vendorId}")
    public String editBudgetCost(@PathVariable Long vendorId, Model model, HttpServletRequest request){
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        Vendor vendor = vendorUtility.findById(vendorId);
        BudgetEntry budgetEntry = budgetEntryService.findBudgetEntryByCustomerAndVendor(customer, vendor);
        model.addAttribute("customer", customer);
        model.addAttribute("budgetEntry", budgetEntry);
        return ("/clients_budgetTracker");
    }
    @PostMapping("/budget_tracker/edit")
    public String postBudgetCost(@CurrentSecurityContext(expression = "authentication?.name") String username, @ModelAttribute("budgetEntry") BudgetEntry budgetEntry, @ModelAttribute("vendor") Vendor vendor, Model model, HttpServletRequest request){
        refactorThisMethod(username, model, request);
        List<BudgetEntry> budgetEntries = (List<BudgetEntry>) request.getSession().getAttribute("budgetEntries");
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        budgetEntry.setCustomer(customer);
        budgetEntry.setVendor(vendor);
        for (BudgetEntry entry : budgetEntries) {
            if (entry.getCustomer().equals(budgetEntry.getCustomer()) && entry.getVendor().equals(budgetEntry.getVendor())) {
                entry.setAmount(budgetEntry.getAmount());
            }
        }
        budgetEntryService.save(budgetEntries);
        return ("/clients_budgetTracker");
    }

    public BudgetEntry createBudgetEntry(Customer customer, Vendor vendor){
        BudgetEntry budgetEntry = new BudgetEntry();
        budgetEntry.setCustomer(customer);
        budgetEntry.setVendor(vendor);
        budgetEntry.setAmount(BigDecimal.valueOf(0));
        return budgetEntry;
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