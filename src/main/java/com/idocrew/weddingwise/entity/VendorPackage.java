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
@Entity
@Table(name = "vendor_packages", schema = "weddingwise")
public class VendorPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name="title", columnDefinition="varchar(50)")
    private String title;

    @Column(name="description", columnDefinition="varchar(1000)")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vendor_id", nullable = false, referencedColumnName = "id", columnDefinition = "bigint(20)")
    private Vendor vendor;
}