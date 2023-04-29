package com.idocrew.weddingwise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "djs_and_live_bands_music_genres", schema = "weddingwise",
    indexes = {
        @Index(name = "music_genre_id", columnList = "music_genre_id"),
        @Index(name = "dj_or_live_band_id", columnList = "dj_or_live_band_id")})
public class MusicVendorGenre {

    public MusicVendorGenre(MusicVendor musicVendor, MusicGenre musicGenre) {
        this.musicVendor = musicVendor;
        this.musicGenre = musicGenre;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dj_or_live_band_id", nullable = false)
    private MusicVendor musicVendor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "music_genre_id", nullable = false)
    private MusicGenre musicGenre;

}