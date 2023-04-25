package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.VendorPhotoFormat;
import com.idocrew.weddingwise.repositories.VendorsPhotoFormatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("vendorPhotoFormatService")
public class VendorPhotoFormatServiceImpl implements VendorPhotoFormatService {
    private final VendorsPhotoFormatRepository vendorsPhotoFormatRepository;
    @Override
    public VendorPhotoFormat saveVendorPhotoFormat(VendorPhotoFormat vendorPhotoFormat) {
        return vendorsPhotoFormatRepository.save(vendorPhotoFormat);
    }
}
