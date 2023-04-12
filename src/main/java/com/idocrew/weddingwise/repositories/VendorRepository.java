package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Vendor findById(long id);
    Vendor findByTitle(String title);
    List<Vendor> findByCategoriesId(long id);
}
