package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.MusicVendor;
import com.idocrew.weddingwise.entity.Vendor;

public interface MusicVendorService {
    MusicVendor saveMusicVendor(MusicVendor musicVendor);

    MusicVendor findByVendor(Vendor vendor);
}
