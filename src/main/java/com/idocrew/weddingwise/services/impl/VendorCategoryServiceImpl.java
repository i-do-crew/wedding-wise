package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.VendorCategory;
import com.idocrew.weddingwise.repositories.VendorCategoryRepository;
import com.idocrew.weddingwise.services.VendorCategoryService;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service("vendorCategoryService")
class VendorCategoryServiceImpl implements VendorCategoryService {
    private final VendorCategoryRepository vendorCategoryRepository;
    private final EntityManager em;
    @Override
    public VendorCategory findById(long id) {
        String query = String.format("select vc.* from vendor_categories vc where vc.id=%d;", id);
        return (VendorCategory) em.createNativeQuery(query, VendorCategory.class).getSingleResult();
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
