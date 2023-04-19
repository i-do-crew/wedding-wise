package com.idocrew.weddingwise.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vendors_music_genres", schema = "weddingwise")
public class VendorsMusicGenre {
    @EmbeddedId
    private VendorsMusicGenreId id;

    @MapsId("id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    private MusicGenre id1;

    @MapsId("vendorId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

}