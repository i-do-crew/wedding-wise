package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.MusicGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicGenreRepository extends JpaRepository<MusicGenre, Long> {
    MusicGenre findByTitle(String title);
}
