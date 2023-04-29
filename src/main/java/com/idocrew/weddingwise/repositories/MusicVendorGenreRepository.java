package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.MusicVendor;
import com.idocrew.weddingwise.entity.MusicVendorGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface MusicVendorGenreRepository extends JpaRepository<MusicVendorGenre, Long> {
    Set<MusicVendorGenre> findMusicVendorGenreByMusicVendor(MusicVendor musicVendor);
}
