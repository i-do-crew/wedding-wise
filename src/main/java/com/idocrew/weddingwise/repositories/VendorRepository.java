package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entities.VendorCategory;
import com.idocrew.weddingwise.entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Vendor findById(long id);
    Vendor findByBusinessName(String businessName);
    List<Vendor> findByVendorCategory(VendorCategory vendorCategory);
}
