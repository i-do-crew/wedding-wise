package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.entity.Vendor;
import com.idocrew.weddingwise.repositories.CategoryRepository;
import com.idocrew.weddingwise.repositories.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VendorController {

    private final VendorRepository vendorRepository;
    private final CategoryRepository categoryRepository;
    @GetMapping("/vendors/{id}")
    public String showVendor(@PathVariable long id, Model model) {
        model.addAttribute("vendor", vendorRepository.findById(id));
        return "vendors/each_vendorCategories";
    }
    @GetMapping("/vendors/categories/{id}")
    public String vendorCategory(@PathVariable long id, Model model){
        model.addAttribute("vendors",
                vendorRepository.findByCategory(categoryRepository.findById(id)));
        //TODO: replace this view with one that shows all vendors in a category
        return "vendors/each_vendorCategories";
    }
    @GetMapping("/vendors")
    public String vendorCategories(Model model){
        List<Vendor> vendors = vendorRepository.findAll();
        model.addAttribute("vendors",vendors);
        return "vendors/all_vendorCategories";
    }
    @GetMapping("/vendor/profile")
    public String vendorProfile(){
        return "vendor_views/vendor_profile";
    }

}
