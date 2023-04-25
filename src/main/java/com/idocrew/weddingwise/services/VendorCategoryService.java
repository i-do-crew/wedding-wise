package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.VendorCategory;

import java.util.Collection;
import java.util.List;

public interface VendorCategoryService {
    VendorCategory findById(long id);
    VendorCategory findByTitle(String title);
    List<VendorCategory> findAll();
}
