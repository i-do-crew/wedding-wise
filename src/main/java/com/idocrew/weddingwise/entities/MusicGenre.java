package com.idocrew.weddingwise.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "music_genres", schema = "weddingwise")
public class MusicGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @OneToMany(mappedBy = "musicGenre")
    private Set<VendorsMusicGenre> vendorsMusicGenres = new LinkedHashSet<>();

    @OneToMany(mappedBy = "musicGenre")
    private Set<DjsAndLiveBandsMusicGenre> djsAndLiveBandsMusicGenres = new LinkedHashSet<>();
}
