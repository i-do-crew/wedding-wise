package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.MusicVendor;
import com.idocrew.weddingwise.entity.Vendor;
import com.idocrew.weddingwise.repositories.MusicVendorRepository;
import com.idocrew.weddingwise.services.MusicVendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("musicVendorService")
public class MusicVendorServiceImpl implements MusicVendorService {
    private final MusicVendorRepository musicVendorRepository;
    @Override
    public MusicVendor saveMusicVendor(MusicVendor musicVendor) {
        return musicVendorRepository.save(musicVendor);
    }

    @Override
    public MusicVendor findByVendor(Vendor vendor) {
        return musicVendorRepository.findMusicVendorByVendor(vendor);
    }
}
