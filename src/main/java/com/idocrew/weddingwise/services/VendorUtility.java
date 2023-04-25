package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.Vendor;

import java.util.Collection;
import java.util.List;


public interface VendorUtility {
    Vendor findById(long id);
    List<Vendor> findAll();
    Vendor findByBusinessName(String name);
    Vendor createVendor(Vendor vendor);
    void deleteVendor(Vendor vendor);
    Vendor saveVendor(Vendor vendorEntity);
}
