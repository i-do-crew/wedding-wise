package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.VendorCategory;

import java.util.Collection;

public interface VendorCategoryService {
    Collection<VendorCategory> findAll();
}
