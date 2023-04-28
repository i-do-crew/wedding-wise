package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.entity.*;
import com.idocrew.weddingwise.services.CustomerVendorService;
import com.idocrew.weddingwise.services.UserService;
import com.idocrew.weddingwise.services.VendorCategoryService;
import com.idocrew.weddingwise.services.VendorUtility;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class VendorController {

    private final VendorUtility vendorUtility;
    private final VendorCategoryService vendorCategoryService;
    private final UserService userService;
    private final CustomerVendorService customerVendorService;

    private void refactorThisMethod(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model, HttpServletRequest request) {
        User user = userService.findByUsername(username);
        //Customer customer = customerService.findCustomerByUser(user);
        List<VendorCategory> vendorCategories = vendorCategoryService.findAll();
        //List<BudgetEntry> budget = budgetEntryService.findBudgetEntriesByCustomer(customer);
        //Set<CustomerVendor> customerVendors = customerVendorService.findByCustomer(customer);
        Vendor vendor = vendorUtility.findVendorByUser(user);
        //request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("vendor", vendor);
        //request.getSession().setAttribute("customer", customer);
        //request.getSession().setAttribute("budget", budget);
        //request.getSession().setAttribute("customerVendors", customerVendors);
        request.getSession().setAttribute("categories", vendorCategories);
    }
    @GetMapping("/vendors/individual/{id}")
    public String showVendor(@PathVariable long id,@CurrentSecurityContext(expression="authentication?.name") String username, Model model, HttpServletRequest request) {
        refactorThisMethod(username, model, request);
        model.addAttribute("vid",id);
        Vendor vendor = vendorUtility.findById(id);
        model.addAttribute("vendor",vendor);
        model.addAttribute("user", vendor.getUser());
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        //Query Customer_Vendor table to determine if there is row that has this VendorID and CustomerID
        //Instantiate customerVendor with that record if it exists otherwise instantiate new CustomerVendor
        Optional<CustomerVendor> optionalCV = customerVendorService.findByCustomerAndVendor(customer, vendor);
        CustomerVendor customerVendor = optionalCV.orElse(new CustomerVendor());
        customerVendor.setVendor(vendor);
        customerVendor.setCustomer(customer);
        model.addAttribute("customerVendor", customerVendor);
        return "vendors/individual_vendor";
    }
    @GetMapping("/vendors/categories/{id}")
    public String vendorCategory(@PathVariable long id, @CurrentSecurityContext(expression="authentication?.name") String username, Model model, HttpServletRequest request){
        refactorThisMethod(username, model, request);
        model.addAttribute("id",id);
        VendorCategory vendorCategory = vendorCategoryService.findById(id);
        model.addAttribute("vendorCategory", vendorCategory);
        model.addAttribute("vendors", vendorUtility.findByCategory(vendorCategory));

        Customer customer = (Customer) request.getSession().getAttribute("customer");
        Vendor vendor = vendorUtility.findById(id);

        Optional<CustomerVendor> optionalCV = customerVendorService.findByCustomerAndVendor(customer, vendor);
        CustomerVendor customerVendor = optionalCV.orElse(new CustomerVendor());
        customerVendor.setVendor(vendor);
        customerVendor.setCustomer(customer);
        request.getSession().setAttribute("customerVendor", customerVendor);
        model.addAttribute("customerVendor", customerVendor);

        return "vendors/each_vendorCategories";
    }
    @GetMapping("/vendors")
    public String vendorCategories(Model model){
        model.addAttribute("vendorCategories", vendorCategoryService.findAll());
        return "vendors/all_vendorCategories";
    }
    @GetMapping("/vendor/profile")
    @PreAuthorize("hasRole('VENDOR')")
    public String vendorProfile(@CurrentSecurityContext(expression = "authentication?.name")String username, Model model, HttpServletRequest request) {
        refactorThisMethod(username, model, request);
        return "vendor_views/vendor_profile";
    }

}
