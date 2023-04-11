package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Vendor findById(long id);
    Vendor findByTitle(String title);
    Vendor findByCategoriesId(long id);
}
