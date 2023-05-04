package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.User;
import com.idocrew.weddingwise.entity.Vendor;
import com.idocrew.weddingwise.entity.VendorCategory;
import com.idocrew.weddingwise.entity.VendorComposite;

import java.util.List;


public interface VendorUtility {
    Vendor findById(long id);
    List<Vendor> findAll();
    List<Vendor> findByCategory(VendorCategory category);
    Vendor findByBusinessName(String name);
    Vendor createVendor(Vendor vendor);
    void deleteVendor(Vendor vendor);
    Vendor saveVendor(Vendor vendorEntity);
    Vendor findVendorByUser(User user);
    void savedVendorAttributes(VendorComposite vendorComposite, Vendor vendorEntity);
}
