package com.idocrew.weddingwise.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="vendors")
@Entity
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="business_name", columnDefinition="varchar(50)")
    private String businessName;
    @ManyToOne
    @JoinColumn(name="category_id", columnDefinition="bigint(20)")
    private VendorCategory vendorCategory;
    @Column(name="about", columnDefinition="longtext")
    private String about;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", columnDefinition = "bigint(20)")
    private User user;
    @OneToMany(mappedBy = "vendor", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private List<VendorPackage> vendorPackages;
    @OneToMany(mappedBy = "vendor", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private List<Venue> venues;
}