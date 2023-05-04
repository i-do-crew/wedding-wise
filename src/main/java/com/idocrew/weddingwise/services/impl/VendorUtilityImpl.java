package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.*;
import com.idocrew.weddingwise.repositories.VendorRepository;
import com.idocrew.weddingwise.services.MusicVendorGenreService;
import com.idocrew.weddingwise.services.MusicVendorService;
import com.idocrew.weddingwise.services.VendorUtility;
import com.idocrew.weddingwise.services.VenueService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service("vendorUtility")
public class VendorUtilityImpl implements VendorUtility {

    private final VendorRepository vendorRepository;
    private final EntityManager em;
    private MusicVendorService musicVendorService;
    private MusicVendorGenreService musicVendorGenreService;
    private VendorPhotoFormatService vendorPhotoFormatService;
    private VenueService venueService;

    @Override
    public Vendor findById(long id) {
        //String query = String.format("select * from vendors where id = %d", id);
        //return (Vendor) em.createNativeQuery(query, Vendor.class).getSingleResult();
        return vendorRepository.findById(id);
    }

    @Override
    public List<Vendor> findAll() {
        return vendorRepository.findAll();
    }

    @Override
    public List<Vendor> findByCategory(VendorCategory category) {
        return vendorRepository.findByVendorCategory(category);
    }

    @Override
    public Vendor findByBusinessName(String businessName) {
        return vendorRepository.findVendorByBusinessName(businessName);
    }

    @Override
    public Vendor createVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    @Override
    public Vendor saveVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    @Override
    public Vendor findVendorByUser(User user) {
        return vendorRepository.findVendorByUser(user);
    }

    @Override
    public void savedVendorAttributes(VendorComposite vendorComposite, Vendor vendorEntity) {
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
    }

    @Override
    public void deleteVendor(Vendor vendor) {
        vendorRepository.delete(vendor);
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

}
