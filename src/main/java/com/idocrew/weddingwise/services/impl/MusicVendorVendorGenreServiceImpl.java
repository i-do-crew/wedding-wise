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
@Service("djsAndLiveBandsMusicGenreService")
public class MusicVendorVendorGenreServiceImpl implements MusicVendorGenreService {
    private final MusicVendorGenreRepository musicVendorGenreRepository;
    @Override
    public MusicVendorGenre saveDjsAndLiveBandsMusicGenre(MusicVendorGenre musicVendorGenre) {
        return musicVendorGenreRepository.save(musicVendorGenre);
    }

    @Override
    public void saveAllDjsAndLiveBandsMusicGenres(Collection<MusicVendorGenre> musicVendorGenres) {
        musicVendorGenreRepository.saveAll(musicVendorGenres);
    }

    @Override
    public Set<MusicVendorGenre> findDjsAndLiveBandsMusicGenreByDjOrLiveBand(MusicVendor musicVendor) {
        return musicVendorGenreRepository.findMusicVendorGenreByMusicVendor(musicVendor);
    }
}
