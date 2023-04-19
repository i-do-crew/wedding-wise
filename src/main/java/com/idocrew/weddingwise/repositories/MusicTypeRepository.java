package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.DjsAndLiveBandsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MusicTypeRepository extends JpaRepository<DjsAndLiveBandsCategory, Long> {
    DjsAndLiveBandsCategory findByTitle(String title);
    DjsAndLiveBandsCategory findById(long id);
    List<DjsAndLiveBandsCategory> findAll();
}