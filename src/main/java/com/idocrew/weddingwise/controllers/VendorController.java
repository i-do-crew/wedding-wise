package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.entity.*;
import com.idocrew.weddingwise.services.*;
import com.idocrew.weddingwise.services.impl.VendorPhotoFormatService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class VendorController {

    private final VendorUtility vendorUtility;
    private final VendorCategoryService vendorCategoryService;
    private final UserService userService;
    private final MusicGenreService musicGenreService;
    private final PhotoFormatService photoFormatService;
    private final MusicVendorCategoryService musicVendorCategoryService;
    @Value("#{'${us.states}'.split(',')}")
    private final String[] states;
    private final UserRegistrationService userRegistrationService;
    private final MusicVendorService musicVendorService;
    private final MusicVendorGenreService musicVendorGenreService;
    private final VendorPhotoFormatService vendorPhotoFormatService;
    private final VenueService venueService;
    private final CustomerService customerService;
    private final BudgetEntryService budgetEntryService;
    private final CustomerVendorService customerVendorService;

    private void refactorThisMethod(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model, HttpServletRequest request) {
        User user = userService.findByUsername(username);
        Vendor vendor = vendorUtility.findVendorByUser(user);
        List<VendorCategory> vendorCategories = vendorCategoryService.findAll();
        request.getSession().setAttribute("vendor", vendor);
        request.getSession().setAttribute("categories", vendorCategories);
        request.getSession().setAttribute("vendor", vendor);
        request.getSession().setAttribute("vendorComposite", new VendorComposite());
    }
    @GetMapping("/vendors/individual/{id}")
    public String showVendor(@PathVariable long id,@CurrentSecurityContext(expression="authentication?.name") String username, Model model, HttpServletRequest request) {
        refactorThisMethod(username, model, request);
        model.addAttribute("vid",id);
        Vendor vendor = vendorUtility.findById(id);
        model.addAttribute("vendor",vendor);
        model.addAttribute("user", vendor.getUser());
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        Optional<CustomerVendor> optionalCV = customerVendorService.findByCustomerAndVendor(customer, vendor);
        CustomerVendor customerVendor = optionalCV.orElse(new CustomerVendor());
        customerVendor.setVendor(vendor);
        customerVendor.setCustomer(customer);
        model.addAttribute("customerVendor", customerVendor);
        model.addAttribute("customer", customer);
        return "vendors/individual_vendor";
    }
    @GetMapping("/vendors/categories/{id}")
    public String vendorCategory(@PathVariable long id, @CurrentSecurityContext(expression="authentication?.name") String username, Model model, HttpServletRequest request){
        model.addAttribute("id",id);
        User user = (User) request.getSession().getAttribute("user");
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        model.addAttribute("user", user);
        model.addAttribute("customer", customer);
        VendorCategory vendorCategory = vendorCategoryService.findById(id);
        model.addAttribute("vendorCategory", vendorCategory);
        model.addAttribute("vendors", vendorUtility.findByCategory(vendorCategory));
        refactorThisMethod(username, model, request);
        return "vendors/each_vendorCategories";
    }
    @GetMapping("/vendors")
    public String vendorCategories(Model model, HttpServletRequest request){
        model.addAttribute("vendorCategories", vendorCategoryService.findAll());
        User user = (User) request.getSession().getAttribute("user");
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        model.addAttribute("user", user);
        model.addAttribute("customer", customer);
        return "vendors/all_vendorCategories";
    }
    @GetMapping("/vendor/profile")
    @PreAuthorize("hasRole('VENDOR')")
    public String vendorProfile(@CurrentSecurityContext(expression = "authentication?.name")String username, Model model, HttpServletRequest request) {
        User user = userService.findByUsername(username);
        List<VendorCategory> vendorCategories = vendorCategoryService.findAll();
        request.getSession().setAttribute("categories", vendorCategories);
        request.getSession().setAttribute("user", user);
        model.addAttribute("options", states);
        model.addAttribute("vendorCategories", vendorCategoryService.findAll());
        model.addAttribute("musicGenres", musicGenreService.findAll());
        model.addAttribute("photoFormats", photoFormatService.findAll());
        model.addAttribute("musicTypes", musicVendorCategoryService.findAll());
        Vendor vendor = vendorUtility.findVendorByUser(user);
        VendorComposite vendorComposite =  new VendorComposite();
        vendorComposite.setVendor(vendor);
        request.getSession().setAttribute("vendor", vendor);
        request.getSession().setAttribute("vendorComposite", vendorComposite);

        switch(vendor.getVendorCategory().getTitle()) {
            case "Venues" -> {
                List<Venue> venues = venueService.findByVendor(vendor);
                if (!venues.isEmpty()) {
                    vendorComposite.setVenue(venues.get(0));
                }
            }
            case "Photographers" -> vendorComposite.setPhotoFormat(vendorPhotoFormatService.findByVendor(vendor));
            case "Bands and DJs" -> {
                MusicVendor musicVendor = musicVendorService.findByVendor(vendor);
                vendorComposite.setMusicVendorCategory(musicVendor.getMusicVendorCategory());
                Set<MusicGenre> genres = musicVendorGenreService
                        .findMusicVendorMusicGenreByMusicVendor(musicVendor)
                        .stream()
                        .map(MusicVendorGenre::getMusicGenre)
                        .collect(Collectors.toSet());
                vendorComposite.setMusicGenres(genres);
            }
            default -> {
            }
        }
        model.addAttribute("vendorComposite", vendorComposite);
        return "vendor_views/vendor_profile";
    }

    @PostMapping("/vendor/profile/edit")
    @Transactional
    public String vendorProfileEditPost(@ModelAttribute("vendorComposite") VendorComposite vendorComposite, HttpServletRequest request, Model model) {
        Vendor vendorEntity = vendorComposite.getVendor();
        User userTemp = vendorEntity.getUser();
        User userEntity = (User) request.getSession().getAttribute("user");
        userEntity.setEmail(userTemp.getEmail());
        userEntity.setFirstName(userTemp.getFirstName());
        userEntity.setLastName(userTemp.getLastName());
        userEntity.setCity(userTemp.getCity());
        userEntity.setState(userTemp.getState());

        model.addAttribute("vendorCategories", vendorCategoryService.findAll());
        model.addAttribute("options", states);
        model.addAttribute("musicGenres", musicGenreService.findAll());
        model.addAttribute("photoFormats", photoFormatService.findAll());
        model.addAttribute("musicTypes", musicVendorCategoryService.findAll());

        userService.saveUser(userEntity);
        vendorUtility.saveVendor(vendorEntity);
        switch (vendorEntity.getVendorCategory().getTitle()) {
            case "Venues" -> saveVenue(vendorEntity, vendorComposite);
            case "Photographers" -> savePhotographer(vendorEntity, vendorComposite.getPhotoFormat());
            case "Bands and DJs" -> {
                MusicVendor musicVendor = saveMusicVendor(vendorEntity, vendorComposite.getMusicVendorCategory());
                saveMusicVendorMusicGenres(musicVendor, vendorComposite.getMusicGenres());
            }
            default -> {
            }
        }
        return "vendor_views/vendor_profile";
    }

//    @GetMapping("/vendor/profile/edit/about")
//    public String vendorProfileEditAbout(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model, HttpServletRequest request){
//        refactorThisMethod(username, model, request);
//        Vendor vendor = (Vendor) request.getSession().getAttribute("vendor");
//        VendorComposite vendorComposite = new VendorComposite();
//        vendorComposite.setVendor(vendor);
//        model.addAttribute("vendorComposite", vendorComposite);
//        return "vendor_views/vendor_profile";
//    }

    @PostMapping("/vendor/profile/edit/about")
    public String vendorProfileEditAboutPost(@ModelAttribute("vendorComposite") VendorComposite vendorComposite){
        Vendor vendorEntity = vendorComposite.getVendor();
        saveVendor(vendorEntity);
        return "vendor_views/vendor_profile";
    }

    private MusicVendor saveMusicVendor(Vendor vendorEntity, MusicVendorCategory category) {
        MusicVendor musicVendor = new MusicVendor(vendorEntity, category);
        if (musicVendor.getId() > 0) {
            return musicVendorService.saveMusicVendor(musicVendor);
        }
        return null;
    }

    private void saveMusicVendorMusicGenres(MusicVendor musicVendor, Set<MusicGenre> musicGenres) {
        Set<MusicVendorGenre> set = musicGenres
                .stream()
                .map(musicGenre -> new MusicVendorGenre(musicVendor, musicGenre))
                .collect(Collectors.toSet());
        if (musicGenres != null) {
            musicVendorGenreService.saveAllMusicVendorMusicGenres(set);
        }
    }

    private void savePhotographer(Vendor vendorEntity, PhotoFormat photoFormat) {
        VendorPhotoFormat vendorPhotoFormatEntity = new VendorPhotoFormat();
        vendorPhotoFormatEntity.setVendor(vendorEntity);
        vendorPhotoFormatEntity.setPhotoFormat(photoFormat);
        if (vendorPhotoFormatEntity.getId() > 0) {
            vendorPhotoFormatService.saveVendorPhotoFormat(vendorPhotoFormatEntity);
        }
    }

    private void saveVenue(Vendor vendorEntity, VendorComposite vendorComposite) {
        Venue venueEntity = new Venue();
        BeanUtils.copyProperties(vendorComposite.getVenue(), venueEntity);
        venueEntity.setVendor(vendorEntity);
        if (venueEntity.getId() > 0) {
            venueService.saveVenue(venueEntity);
        }
    }

    private void saveVendor(Vendor vendor) {
        Vendor vendorEntity = new Vendor();
        BeanUtils.copyProperties(vendor, vendorEntity);
        if(vendorEntity.getId() > 0) {
            vendorUtility.saveVendor(vendorEntity);
        }
    }

}
