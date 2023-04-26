package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.MusicVendor;
import com.idocrew.weddingwise.entity.MusicVendorCategory;
import com.idocrew.weddingwise.entity.Vendor;
import com.idocrew.weddingwise.repositories.MusicVendorCategoryRepository;
import com.idocrew.weddingwise.services.MusicVendorCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@AllArgsConstructor
@Service("djsAndLiveBandsCategoryService")
class MusicVendorCategoryServiceImpl implements MusicVendorCategoryService {
    private final MusicVendorCategoryRepository musicVendorCategoryRepository;
    @Override
    public Collection<MusicVendorCategory> findAll() {
        return musicVendorCategoryRepository.findAll();
    }

    @Override
    public MusicVendorCategory findByVendor(MusicVendor musicVendor) {
        return musicVendorCategoryRepository.findMusicVendorCategoryByMusicVendor(musicVendor);
    }
}
