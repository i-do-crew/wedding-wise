package com.idocrew.weddingwise.entity;

import jakarta.persistence.*;

import java.util.Set;

public class MusicType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="title", columnDefinition="varchar(50)")
    private String title;

    @ManyToOne
    @JoinColumn(name="vendor_id", columnDefinition="bigint(20)")
    private Set<Vendor> vendors;
}
