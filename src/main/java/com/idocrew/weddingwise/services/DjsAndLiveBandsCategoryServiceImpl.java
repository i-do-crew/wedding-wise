package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.DjsAndLiveBandsCategory;
import com.idocrew.weddingwise.repositories.DjsAndLiveBandsCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@AllArgsConstructor
@Service("djsAndLiveBandsCategoryService")
class DjsAndLiveBandsCategoryServiceImpl implements DjsAndLiveBandsCategoryService {
    private final DjsAndLiveBandsCategoryRepository djsAndLiveBandsCategoryRepository;
    @Override
    public Collection<DjsAndLiveBandsCategory> findAll() {
        return djsAndLiveBandsCategoryRepository.findAll();
    }
}
