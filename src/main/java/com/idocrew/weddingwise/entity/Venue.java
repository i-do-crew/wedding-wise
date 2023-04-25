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
@Table(name = "venues", schema = "weddingwise")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name="title", columnDefinition="varchar(50)")
    private String title;

    @Column(name="address", columnDefinition="varchar(50)")
    private String address;

    @Column(name="city", columnDefinition="varchar(25)", nullable = false)
    private String city;

    @Column(name="state", columnDefinition="varchar(25)", nullable = false)
    private String state;

    @Column(name="zip", columnDefinition="varchar(5)", nullable = false)
    private String zip;

    @Column(name="capacity", columnDefinition="int", nullable = false)
    private int capacity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="vendor_id", referencedColumnName="id", columnDefinition = "bigint(20)", nullable = false)
    private Vendor vendor;
}
