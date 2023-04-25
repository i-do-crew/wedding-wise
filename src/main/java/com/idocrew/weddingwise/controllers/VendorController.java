package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.entity.VendorCategory;
import com.idocrew.weddingwise.services.VendorCategoryService;
import com.idocrew.weddingwise.services.VendorUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class VendorController {

    private final VendorUtility vendorUtility;
    private final VendorCategoryService vendorCategoryService;
    @GetMapping("/vendors/individual/{id}")
    public String showVendor(@PathVariable long id, Model model) {
//        model.addAttribute("vendor", vendorRepository.findById(id));
        model.addAttribute("vid",id);
        return "vendors/individual_vendor";
    }
    @GetMapping("/vendors/categories/{id}")
    public String vendorCategory(@PathVariable long id, Model model){
        VendorCategory vendorCategory = vendorCategoryService.findById(id);
        model.addAttribute("vendorCategory", vendorCategory);
        model.addAttribute("vendors", vendorUtility.findByCategory(vendorCategory));
        return "vendors/each_vendorCategories";
    }
    @GetMapping("/vendors")
    public String vendorCategories(Model model){
        model.addAttribute("vendorCategories", vendorCategoryService.findAll());
        return "vendors/all_vendorCategories";
    }
    @GetMapping("/vendor/profile")
    public String vendorProfile() {
        return "vendor_views/vendor_profile";
    }
    @GetMapping("/likedVendors")
    public String likedVendors() {
        return "/likedVendors";
    }

}
