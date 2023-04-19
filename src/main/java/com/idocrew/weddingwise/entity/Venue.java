package com.idocrew.weddingwise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="venues")
@Entity
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="title", columnDefinition="varchar(50)")
    private String title;

    @Column(name="address", columnDefinition="varchar(50)")
    private String address;

    @Column(name="city", columnDefinition="varchar(25)")
    private String city;

    @Column(name="state", columnDefinition="varchar(25)")
    private String state;

    @Column(name="zip", columnDefinition="varchar(5)")
    private String zip;

    @Column(name="capacity", columnDefinition="varchar(50)")
    private int capacity;

    @ManyToOne
    @JoinColumn(name="vendor_id", referencedColumnName="id", columnDefinition = "bigint(20)")
    private Vendor vendor;
}
