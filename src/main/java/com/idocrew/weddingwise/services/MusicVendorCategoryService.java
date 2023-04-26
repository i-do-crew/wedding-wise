package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.MusicVendor;
import com.idocrew.weddingwise.entity.MusicVendorCategory;
import com.idocrew.weddingwise.entity.Vendor;

import java.util.Collection;

public interface MusicVendorCategoryService {
    Collection<MusicVendorCategory> findAll();
}
