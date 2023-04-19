package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.MusicType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MusicTypeRepository extends JpaRepository<MusicType, Long> {
    MusicType findByTitle(String title);
    MusicType findById(long id);
    List<MusicType> findAll();
}