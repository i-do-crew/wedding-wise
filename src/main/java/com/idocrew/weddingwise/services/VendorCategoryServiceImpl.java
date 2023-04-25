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
    public VendorCategory findById(long id) {
        return vendorCategoryRepository.getReferenceById(id);
    }

    @Override
    public VendorCategory findByTitle(String title) {
        return vendorCategoryRepository.findByTitle(title);
    }

    @Override
    public Collection<VendorCategory> findAll() {
        return vendorCategoryRepository.findAll();
    }
}
