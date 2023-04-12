package com.idocrew.weddingwise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="vendor_packages")
@Entity
public class VendorPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="title", columnDefinition="varchar(50)")
    private String title;

    @Column(name="description", columnDefinition="varchar(1000)")
    private String description;

    @ManyToOne(optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "vendor_id", referencedColumnName = "id", columnDefinition = "bigint(20)")
    private Vendor vendor;
}