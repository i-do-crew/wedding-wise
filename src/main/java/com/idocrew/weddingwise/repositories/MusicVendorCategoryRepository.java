package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.MusicVendor;
import com.idocrew.weddingwise.entity.MusicVendorCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicVendorCategoryRepository extends JpaRepository<MusicVendorCategory, Long> {
}