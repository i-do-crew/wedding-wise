package com.idocrew.weddingwise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "djs_and_live_bands", schema = "weddingwise", indexes = {
        @Index(name = "vendor_id", columnList = "vendor_id")
})
public class DjsAndLiveBand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dalb_category_id", nullable = false)
    private DjsAndLiveBandsCategory dalbCategory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @OneToMany(mappedBy = "djOrLiveBand")
    private Set<DjsAndLiveBandsMusicGenre> djsAndLiveBandsMusicGenres = new LinkedHashSet<>();

}