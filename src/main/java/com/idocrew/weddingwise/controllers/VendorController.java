package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.entity.BudgetEntry;
import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.User;
import com.idocrew.weddingwise.entity.Vendor;
import com.idocrew.weddingwise.repositories.UserRepository;
import com.idocrew.weddingwise.repositories.VendorCategoryRepository;
import com.idocrew.weddingwise.repositories.VendorRepository;
import jakarta.servlet.http.HttpServletRequest;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VendorController {

    private final VendorRepository vendorRepository;
    private final VendorCategoryRepository vendorCategoryRepository;
    private final UserRepository userRepository;

    private void refactorThisMethod(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model, HttpServletRequest request) {
        User user = userRepository.findByUsername(username);
        Vendor vendor = vendorRepository.findVendorByUser(user);

        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("vendor", vendor);
    }
    @GetMapping("/vendors/individual/{id}")
    public String showVendor(@PathVariable long id,@CurrentSecurityContext(expression="authentication?.name") String username, Model model, HttpServletRequest request) {
//        model.addAttribute("vendor", vendorRepository.findById(id));
        refactorThisMethod(username, model, request);
        model.addAttribute("vid",id);
        return "vendors/individual_vendor";
    }
    @GetMapping("/vendors/categories/{id}")
    public String vendorCategory(@PathVariable long id, @CurrentSecurityContext(expression="authentication?.name") String username, Model model, HttpServletRequest request){
        model.addAttribute("id",id);
        model.addAttribute("category", vendorCategoryRepository.findById(id));
        refactorThisMethod(username, model, request);
        return "vendors/each_vendorCategories";
    }
    @GetMapping("/vendors")
    public String vendorCategories(Model model){
        List<Vendor> vendors = vendorRepository.findAll();
        model.addAttribute("vendors",vendors);
        return "vendors/all_vendorCategories";
    }
    @GetMapping("/vendor/profile")
    public String vendorProfile(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model, HttpServletRequest request) {
        refactorThisMethod(username, model, request);
        return "vendor_views/vendor_profile";
    }
    @GetMapping("/likedVendors")
    public String likedVendors() {
        return "/likedVendors";
    }

}
