package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.PhotoFormat;
import com.idocrew.weddingwise.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoFormatRepository extends JpaRepository<PhotoFormat, Long> {
    PhotoFormat findByTitle(String title);
}
