package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.VendorCategory;

import java.util.Collection;

public interface VendorCategoryService {
    VendorCategory findById(long id);
    VendorCategory findByTitle(String title);
    Collection<VendorCategory> findAll();
}
