package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.MusicVendor;
import com.idocrew.weddingwise.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicVendorRepository extends JpaRepository<MusicVendor, Long> {
    MusicVendor findMusicVendorByVendor(Vendor vendor);
}
