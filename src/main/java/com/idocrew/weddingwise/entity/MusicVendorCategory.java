package com.idocrew.weddingwise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "djs_and_live_bands_categories", schema = "weddingwise")
public class MusicVendorCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name="title", columnDefinition="varchar(50)")
    private String title;

    @OneToMany(mappedBy = "musicVendorCategory")
    private Set<MusicVendor> musicVendors = new LinkedHashSet<>();
}
