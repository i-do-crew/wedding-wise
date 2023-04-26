package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.entity.*;
import com.idocrew.weddingwise.services.*;
import com.idocrew.weddingwise.services.impl.VendorPhotoFormatService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class VendorController {

//    private final UserRepository userRepository;
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


    private void refactorThisMethod(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model, HttpServletRequest request) {
        User user = userService.findByUsername(username);
        Vendor vendor = vendorUtility.findVendorByUser(user);
        VendorComposite vendorComposite = new VendorComposite();
        vendorComposite.setVendor(vendor);
        MusicVendor djOrLiveBand = musicVendorService.findByVendor(vendor);
        switch(vendor.getVendorCategory().getTitle()) {
            case "Venues" -> vendorComposite.setVenue(venueService.findByVendor(vendor).get(0));
            case "Photographers" -> vendorComposite.setPhotoFormat(vendorPhotoFormatService.findByVendor(vendor));
            case "Bands and DJs" -> {
                vendorComposite.setMusicVendorCategory(musicVendorCategoryService.findByVendor(vendor));
                Set<MusicGenre> genres = musicVendorGenreService
                        .findDjsAndLiveBandsMusicGenreByDjOrLiveBand(djOrLiveBand)
                        .stream()
                        .map(djGenre -> djGenre.getMusicGenre())
                        .collect(Collectors.toSet());
                vendorComposite.setMusicGenres(genres);
            }
            default -> {
            }
        }

        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("vendor", vendor);
        request.getSession().setAttribute("vendorComposite", vendorComposite);
    }
    @GetMapping("/vendors/individual/{id}")
    public String showVendor(@PathVariable long id,@CurrentSecurityContext(expression="authentication?.name") String username, Model model, HttpServletRequest request) {
//        model.addAttribute("vendor", vendorRepository.findById(id));
        refactorThisMethod(username, model, request);
        model.addAttribute("vid",id);
        Vendor vendor = vendorUtility.findById(id);
        model.addAttribute("vendor",vendor);
        model.addAttribute("user", vendor.getUser());
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
        User user = userService.findByUsername(username);
        Vendor vendor = vendorUtility.findVendorByUser(user);
        return "vendor_views/vendor_profile";
    }
    @GetMapping("/vendor/profile/edit")
    public String vendorProfileEdit(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model, HttpServletRequest request) {
        refactorThisMethod(username, model, request);
        model.addAttribute("options", states);
        model.addAttribute("vendorCategories", vendorCategoryService.findAll());
        model.addAttribute("musicGenres", musicGenreService.findAll());
        model.addAttribute("photoFormats", photoFormatService.findAll());
        model.addAttribute("musicTypes", musicVendorCategoryService.findAll());

        return "vendor_views/vendor_profile";
    }
    @PostMapping("/vendor/profile/edit")
    public String vendorProfileEditPost(@CurrentSecurityContext(expression = "authentication?.name") String username, Model model, HttpServletRequest request,@ModelAttribute("vendorComposite") VendorComposite vendorComposite) {
//        refactorThisMethod(username, model, request);
//        vendor object tied to the form that you save
        userRegistrationService.register(vendorComposite);
        return "vendor_views/vendor_profile";
    }

    private MusicVendor saveDjOrBand(Vendor vendorEntity, MusicVendorCategory category) {
        MusicVendor djOrLiveBand = new MusicVendor(vendorEntity, category);
        return musicVendorService.saveDjsAndLiveBands(djOrLiveBand);
    }

    private void saveDjOrBandMusicGenres(MusicVendor djsOrLiveBand, Set<MusicGenre> musicGenres) {
        Set<MusicVendorGenre> set = musicGenres
                .stream()
                .map(musicGenre -> new MusicVendorGenre(djsOrLiveBand, musicGenre))
                .collect(Collectors.toSet());
        musicVendorGenreService.saveAllDjsAndLiveBandsMusicGenres(set);
    }

    private void savePhotographer(Vendor vendorEntity, PhotoFormat photoFormat) {
        VendorPhotoFormat vendorPhotoFormatEntity = new VendorPhotoFormat();
        vendorPhotoFormatEntity.setVendor(vendorEntity);
        vendorPhotoFormatEntity.setPhotoFormat(photoFormat);
        vendorPhotoFormatService.saveVendorPhotoFormat(vendorPhotoFormatEntity);
    }

    private void saveVenue(Vendor vendorEntity, VendorComposite vendorComposite) {
        Venue venueEntity = new Venue();
        BeanUtils.copyProperties(vendorComposite.getVenue(), venueEntity);
        venueEntity.setVendor(vendorEntity);
        venueService.saveVenue(venueEntity);
    }

    private Vendor saveVendor(Vendor vendor) {
        Vendor vendorEntity = new Vendor();
        BeanUtils.copyProperties(vendor, vendorEntity);
        return vendorUtility.saveVendor(vendorEntity);
    }
}
