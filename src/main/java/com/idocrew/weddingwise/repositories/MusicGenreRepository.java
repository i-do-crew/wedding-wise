package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entities.MusicGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicGenreRepository extends JpaRepository<MusicGenre, Long> {
    MusicGenre findById(long id);
    List<MusicGenre> findAll();
    MusicGenre findByTitle(String title);
}
