package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.MusicGenre;

import java.util.List;

public interface GenreRepository {
    MusicGenre findById(long id);
    List<MusicGenre> findAll();
    MusicGenre findByTitle(String title);
}
