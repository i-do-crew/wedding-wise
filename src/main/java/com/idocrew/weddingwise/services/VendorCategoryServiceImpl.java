package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.VendorCategory;
import com.idocrew.weddingwise.repositories.VendorCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@AllArgsConstructor
@Service("vendorCategoryService")
class VendorCategoryServiceImpl implements VendorCategoryService {
    private final VendorCategoryRepository vendorCategoryRepository;
    @Override
    public Collection<VendorCategory> findAll() {
        return vendorCategoryRepository.findAll();
    }
}
