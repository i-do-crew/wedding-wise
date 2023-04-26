package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.PhotoFormat;
import com.idocrew.weddingwise.entity.Vendor;
import com.idocrew.weddingwise.entity.VendorPhotoFormat;

public interface VendorPhotoFormatService {
    VendorPhotoFormat saveVendorPhotoFormat(VendorPhotoFormat vendorPhotoFormat);

    PhotoFormat findByVendor(Vendor vendor);
}
