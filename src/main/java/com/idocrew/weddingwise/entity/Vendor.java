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
public class Vendor extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="title", columnDefinition="varchar(50)")
    private String title;
    @ManyToOne
    @JoinColumn(name="category_id", columnDefinition="bigint(20)")
    private Category category;
    @Column(name="city_state", columnDefinition="varchar(25)")
    private String cityState;
    @Column(name="about", columnDefinition="longtext")
    private String about;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", columnDefinition = "bigint(20)")
    private User user;

    @OneToMany(mappedBy = "vendor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<VendorPackage> vendorPackages;
}