package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.User;
import com.idocrew.weddingwise.entity.Vendor;
import com.idocrew.weddingwise.entity.VendorCategory;
import com.idocrew.weddingwise.repositories.VendorRepository;
import com.idocrew.weddingwise.services.VendorUtility;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service("vendorUtility")
public class VendorUtilityImpl implements VendorUtility {

    private final VendorRepository vendorRepository;
    private final EntityManager em;

    @Override
    public Vendor findById(long id) {
        String query = String.format("select * from vendor where id = %d", id);
        Vendor vendor = (Vendor) em.createNativeQuery(query, Vendor.class).getSingleResult();
        return vendor;
        //return vendorRepository.getReferenceById(id);
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
    public void deleteVendor(Vendor vendor) {
        vendorRepository.delete(vendor);
    }
}
