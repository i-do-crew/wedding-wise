package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entities.PhotoFormat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoFormatRepository extends JpaRepository<PhotoFormat, Long> {
    PhotoFormat findByTitle(String title);
    PhotoFormat findById(long id);
    List<PhotoFormat> findAll();
}
