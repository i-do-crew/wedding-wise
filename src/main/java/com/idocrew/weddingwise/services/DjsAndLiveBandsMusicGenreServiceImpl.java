package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.DjsAndLiveBandsMusicGenre;
import com.idocrew.weddingwise.repositories.DjsAndLiveBandsMusicGenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Service("djsAndLiveBandsMusicGenreService")
public class DjsAndLiveBandsMusicGenreServiceImpl implements DjsAndLiveBandsMusicGenreService {
    private final DjsAndLiveBandsMusicGenreRepository djsAndLiveBandsMusicGenreRepository;
    @Override
    public DjsAndLiveBandsMusicGenre saveDjsAndLiveBandsMusicGenre(DjsAndLiveBandsMusicGenre djsAndLiveBandsMusicGenre) {
        return djsAndLiveBandsMusicGenreRepository.save(djsAndLiveBandsMusicGenre);
    }

    @Override
    public void saveAllDjsAndLiveBandsMusicGenres(Collection<DjsAndLiveBandsMusicGenre> djsAndLiveBandsMusicGenres) {
        djsAndLiveBandsMusicGenreRepository.saveAll(djsAndLiveBandsMusicGenres);
    }
}
