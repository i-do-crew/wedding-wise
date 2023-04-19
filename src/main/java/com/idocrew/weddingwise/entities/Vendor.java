package com.idocrew.weddingwise.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "vendors", schema = "weddingwise")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name="business_name", columnDefinition="varchar(50)")
    private String businessName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="category_id", columnDefinition="bigint(20)", nullable = false)
    private VendorCategory vendorCategory;

    @Lob
    @Column(name="about", columnDefinition="longtext")
    private String about;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", columnDefinition = "bigint(20)")
    private User user;

    @OneToMany(mappedBy = "vendor")
    private Set<VendorsMusicGenre> vendorsMusicGenres = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vendor")
    private Set<VendorService> vendorServices = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vendor")
    private Set<VendorsPhotoFormat> vendorsPhotoFormats = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vendor")
    private Set<Venue> venues = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vendor")
    private Set<VendorRatingsReview> vendorRatingsReviews = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vendor")
    private Set<DjsAndLiveBand> djsAndLiveBands = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vendor")
    private Set<VendorPackage> vendorPackages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vendor")
    private Set<CustomerVendor> customerVendors = new LinkedHashSet<>();
}