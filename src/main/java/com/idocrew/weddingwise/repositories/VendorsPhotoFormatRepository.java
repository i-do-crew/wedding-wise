package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.PhotoFormat;
import com.idocrew.weddingwise.entity.Vendor;
import com.idocrew.weddingwise.entity.VendorPhotoFormat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorsPhotoFormatRepository extends JpaRepository<VendorPhotoFormat, Long> {

    PhotoFormat findPhotoFormatByVendor(Vendor vendor);
}
