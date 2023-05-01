package com.idocrew.weddingwise.controllers;


import com.idocrew.weddingwise.entity.*;
import com.idocrew.weddingwise.services.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Controller
public class BudgetController {

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
        Set<CustomerVendor> customerVendors = customerVendorService.findByCustomer(customer);
        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("customer", customer);
        request.getSession().setAttribute("customerVendors", customerVendors);
        request.getSession().setAttribute("categories", vendorCategories);
        model.addAttribute("options", states);
    }
    @GetMapping("/budget")
    public String budgetTracker(@CurrentSecurityContext(expression="authentication?.name") String username, Model model, HttpServletRequest request){
        refactorThisMethod(username, model, request);
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        model.addAttribute("customer", customer);
        BudgetEntry budgetEntry = new BudgetEntry();
        model.addAttribute("budgetEntry", budgetEntry);
        List<BudgetEntry> budgetEntries = budgetEntryService.findBudgetEntriesByCustomer(customer);
        fixBudgetEntryVendors(customer, budgetEntries);
        request.getSession().setAttribute("budgetEntries", budgetEntries);
        BigDecimal balance = customer.getBudget();
        for (BudgetEntry entry: budgetEntries) {
            balance = balance.subtract(entry.getAmount());
        }
        model.addAttribute("currentBalance", balance);
        return "/clients_budgetTracker";
    }

    private void fixBudgetEntryVendors(Customer customer, List<BudgetEntry> budgetEntries) {
        List<SimpleBudgetEntry> simpleBudgetEntries = budgetEntryService.findSimpleBudgetEntriesByCustomer(customer);
        for (int i = 0; i < budgetEntries.size(); i++) {
            Vendor vendor = vendorUtility.findById(simpleBudgetEntries.get(i).getVendorId());
            budgetEntries.get(i).setVendor(vendor);
        }
    }

    @GetMapping("/budget/edit/{vendorId}")
    public String editBudgetCost(@SessionAttribute("customer") Customer customer, @PathVariable Long vendorId){
        Vendor vendor = vendorUtility.findById(vendorId);
        BudgetEntry budgetEntry = budgetEntryService.findBudgetEntryByCustomerAndVendor(customer, vendor);
        budgetEntry.setAmount(BigDecimal.valueOf(0));
        //model.addAttribute("customer", customer);
        //model.addAttribute("budgetEntry", budgetEntry);
        return ("/clients_budgetTracker");
    }
    @PostMapping("/budget/edit")
    public String postBudgetCost(@CurrentSecurityContext(expression = "authentication?.name") String username,
                                 @ModelAttribute("budgetEntry") BudgetEntry budgetEntry,
                                 @ModelAttribute("vendor") Vendor vendor, Model model, HttpServletRequest request){
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
    //create
    //read
    //update
    //delete

}
