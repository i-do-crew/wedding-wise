package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.Vendor;
import com.idocrew.weddingwise.repositories.VendorRepository;
import com.idocrew.weddingwise.services.VendorUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service("vendorUtility")
public class VendorUtilityImpl implements VendorUtility {

    private final VendorRepository vendorRepository;

    @Override
    public Vendor findById(long id) {
        return vendorRepository.getReferenceById(id);
    }

    @Override
    public List<Vendor> findAll() {
        return vendorRepository.findAll();
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
    public void deleteVendor(Vendor vendor) {
        vendorRepository.delete(vendor);
    }
}
