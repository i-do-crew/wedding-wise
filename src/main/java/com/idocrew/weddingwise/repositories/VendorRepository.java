package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.Vendor;
import com.idocrew.weddingwise.entity.VendorCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Vendor findVendorByBusinessName(String businessName);
    List<Vendor> findByVendorCategory(VendorCategory vendorCategory);
}
