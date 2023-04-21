package com.idocrew.weddingwise.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="vendor_categories", schema = "weddingwise")
public class VendorCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="title", columnDefinition="varchar(50)")
    private String title;

    @OneToMany(mappedBy = "vendorCategory")
    private Set<Vendor> vendors = new LinkedHashSet<>();
}
