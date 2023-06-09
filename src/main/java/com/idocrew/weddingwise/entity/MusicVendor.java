package com.idocrew.weddingwise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "djs_and_live_bands", schema = "weddingwise", indexes = {
    @Index(name = "vendor_id", columnList = "vendor_id")
})
public class MusicVendor {

    public MusicVendor(Vendor vendor, MusicVendorCategory musicVendorCategory) {
        this.vendor = vendor;
        this.musicVendorCategory = musicVendorCategory;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dalb_category_id", nullable = false)
    private MusicVendorCategory musicVendorCategory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @OneToMany(mappedBy = "musicVendor")
    private Set<MusicVendorGenre> musicVendorGenres = new LinkedHashSet<>();

}