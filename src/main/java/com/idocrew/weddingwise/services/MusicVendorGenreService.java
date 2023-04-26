package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.MusicVendor;
import com.idocrew.weddingwise.entity.MusicVendorGenre;

import java.util.Collection;
import java.util.Set;

public interface MusicVendorGenreService {
    MusicVendorGenre saveDjsAndLiveBandsMusicGenre(MusicVendorGenre musicVendorGenre);
    void saveAllDjsAndLiveBandsMusicGenres(Collection<MusicVendorGenre> musicVendorGenres);

    Set<MusicVendorGenre> findDjsAndLiveBandsMusicGenreByDjOrLiveBand(MusicVendor musicVendor);
}
