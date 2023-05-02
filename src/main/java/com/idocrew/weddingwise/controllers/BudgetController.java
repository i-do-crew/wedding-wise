package com.idocrew.weddingwise.controllers;


import com.idocrew.weddingwise.entity.*;
import com.idocrew.weddingwise.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
        request.getSession().setAttribute("budgetEntries", budgetEntries);
        model.addAttribute("budgetEntries", budgetEntries);
        BigDecimal balance = customer.getBudget();
        for (BudgetEntry entry: budgetEntries) {
            balance = balance.subtract(entry.getAmount());
        }
        model.addAttribute("currentBalance", balance);
        return "customer_views/clients_budgetTracker";
    }

    @PostMapping("/budget/edit/{vendorId}")
    public String editBudgetCost(@Valid BudgetEntry budgetEntry,
                                 BindingResult bindingResult, @SessionAttribute("customer") Customer customer,
                                 @PathVariable Long vendorId) {
        if (bindingResult.hasErrors()) {
            return "redirect:/budget";
        }

        Vendor vendor = vendorUtility.findById(vendorId);
        BudgetEntry budgetEntryEntity = budgetEntryService.findBudgetEntryByCustomerAndVendor(customer, vendor);
        budgetEntryEntity.setCustomer(customer);
        budgetEntryEntity.setVendor(vendor);
        budgetEntryEntity.setAmount(budgetEntry.getAmount());
        budgetEntryService.save(budgetEntryEntity);
        return "redirect:/budget";
    }


}
