package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.entity.User;
import com.idocrew.weddingwise.entity.Vendor;
import com.idocrew.weddingwise.entity.VendorCategory;
import com.idocrew.weddingwise.repositories.UserRepository;
import com.idocrew.weddingwise.repositories.VendorCategoryRepository;
import com.idocrew.weddingwise.repositories.VendorRepository;
import com.idocrew.weddingwise.services.UserService;
import com.idocrew.weddingwise.services.VendorCategoryService;
import com.idocrew.weddingwise.services.VendorUtility;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class VendorController {

//    private final UserRepository userRepository;
    private final VendorUtility vendorUtility;
    private final VendorCategoryService vendorCategoryService;
    private final UserService userService;

    private void refactorThisMethod(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model, HttpServletRequest request) {
        User user = userService.findByUsername(username);
        Vendor vendor = vendorUtility.findVendorByUser(user);

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
        VendorCategory vendorCategory = vendorCategoryService.findById(id);
        model.addAttribute("vendorCategory", vendorCategory);
        model.addAttribute("vendors", vendorUtility.findByCategory(vendorCategory));
        refactorThisMethod(username, model, request);
        return "vendors/each_vendorCategories";
    }
    @GetMapping("/vendors")
    public String vendorCategories(Model model){
        model.addAttribute("vendorCategories", vendorCategoryService.findAll());
//        List<Vendor> vendors = vendorRepository.findAll();
//        VendorCategory vendorCategory = vendorCategoryService.findById(id);

//        model.addAttribute("vendors", vendorUtility.findByCategory(vendorCategory));
        return "vendors/all_vendorCategories";
    }
    @GetMapping("/vendor/profile")
    public String vendorProfile(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model, HttpServletRequest request) {
        refactorThisMethod(username, model, request);
        return "vendor_views/vendor_profile";
    }

}
