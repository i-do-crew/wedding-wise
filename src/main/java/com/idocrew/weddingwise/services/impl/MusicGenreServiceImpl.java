package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.MusicGenre;
import com.idocrew.weddingwise.repositories.MusicGenreRepository;
import com.idocrew.weddingwise.services.MusicGenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Service("musicGenreService")
public class MusicGenreServiceImpl implements MusicGenreService {
    private final MusicGenreRepository musicGenreRepository;
    @Override
    public Collection<MusicGenre> findAll() {
        return musicGenreRepository.findAll();
    }
}
