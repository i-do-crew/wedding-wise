package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.repositories.CategoryRepository;
import com.idocrew.weddingwise.repositories.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class VendorController {

    private final VendorRepository vendorRepository;
    private final CategoryRepository categoryRepository;
    @GetMapping("/vendors/{id}")
    public String showVendor(@PathVariable long id, Model model) {
        model.addAttribute("vendor", vendorRepository.findById(id));
        return "each_vendorCategories";
    }
    @GetMapping("/vendors/categories/{id}")
    public String vendorCategory(@PathVariable long id, Model model){
        model.addAttribute("vendors",
                vendorRepository.findByCategory(categoryRepository.findById(id)));
        //TODO: replace this view with one that shows all vendors in a category
        return "each_vendorCategories";
    }
    @GetMapping("/vendors/register")
    public String registerVendors() {
        return "vendor_registration";
    }

}
