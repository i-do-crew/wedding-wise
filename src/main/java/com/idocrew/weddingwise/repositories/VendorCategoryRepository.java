package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.VendorCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorCategoryRepository extends JpaRepository<VendorCategory, Long> {
    VendorCategory findById(long id);
    List<VendorCategory> findAll();
}
