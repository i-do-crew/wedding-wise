package com.idocrew.weddingwise.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Scope("session")
@Table(name = "vendors", schema = "weddingwise")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name="business_name", columnDefinition="varchar(50)")
    private String businessName;

    @ManyToOne(optional = false)
    @JoinColumn(name="category_id", columnDefinition="bigint(20)", nullable = false)
    private VendorCategory vendorCategory;

    @Lob
    @Column(name="about", columnDefinition="longtext")
    private String about;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", columnDefinition = "bigint(20)")
    private User user;

    @OneToMany(mappedBy = "vendor")
    private Set<VendorService> vendorServices = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vendor")
    private Set<VendorPhotoFormat> vendorsPhotoFormats = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vendor")
    private Set<Venue> venues = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vendor")
    private Set<VendorRatingsReview> vendorRatingsReviews = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vendor")
    private Set<MusicVendor> musicVendors = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vendor")
    private Set<VendorPackage> vendorPackages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vendor")
    private Set<CustomerVendor> customerVendors = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vendor", fetch = FetchType.EAGER)
    private List<BudgetEntry> budgetEntries;

}