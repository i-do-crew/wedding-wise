package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.MusicVendor;
import com.idocrew.weddingwise.entity.MusicVendorGenre;
import com.idocrew.weddingwise.repositories.MusicVendorGenreRepository;
import com.idocrew.weddingwise.services.MusicVendorGenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@RequiredArgsConstructor
@Service("musicVendorMusicGenreService")
public class MusicVendorVendorGenreServiceImpl implements MusicVendorGenreService {
    private final MusicVendorGenreRepository musicVendorGenreRepository;
    @Override
    public MusicVendorGenre saveMusicVendorMusicGenre(MusicVendorGenre musicVendorGenre) {
        return musicVendorGenreRepository.save(musicVendorGenre);
    }

    @Override
    public void saveAllMusicVendorMusicGenres(Collection<MusicVendorGenre> musicVendorGenres) {
        musicVendorGenreRepository.saveAll(musicVendorGenres);
    }

    @Override
    public Set<MusicVendorGenre> findMusicVendorMusicGenreByMusicVendor(MusicVendor musicVendor) {
        return musicVendorGenreRepository.findMusicVendorGenreByMusicVendor(musicVendor);
    }
}
