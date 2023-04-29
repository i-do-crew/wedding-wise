package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.VendorCategory;
import com.idocrew.weddingwise.repositories.VendorCategoryRepository;
import com.idocrew.weddingwise.services.VendorCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Service("vendorCategoryService")
class VendorCategoryServiceImpl implements VendorCategoryService {
    private final VendorCategoryRepository vendorCategoryRepository;

    @Override
    public VendorCategory findById(long id) {
        return vendorCategoryRepository.getReferenceById(id);
    }

    @Override
    public VendorCategory findByTitle(String title) {
        return vendorCategoryRepository.findByTitle(title);
    }

    @Override
    public List<VendorCategory> findAll() {
        return vendorCategoryRepository.findAll();
    }
}
