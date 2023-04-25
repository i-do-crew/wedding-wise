package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.DjsAndLiveBandsMusicGenre;

import java.util.Collection;

public interface DjsAndLiveBandsMusicGenreService {
    DjsAndLiveBandsMusicGenre saveDjsAndLiveBandsMusicGenre(DjsAndLiveBandsMusicGenre djsAndLiveBandsMusicGenre);
    void saveAllDjsAndLiveBandsMusicGenres(Collection<DjsAndLiveBandsMusicGenre> djsAndLiveBandsMusicGenres);

}
