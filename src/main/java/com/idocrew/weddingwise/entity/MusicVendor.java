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
@Table(name="djs_and_live_bands")
@Entity
public class MusicVendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="title", columnDefinition="varchar(50)")
    private String title;

    @Column(name="category", columnDefinition="varchar(50)")
    private String category;

    @JoinColumn(name="vendor_id", referencedColumnName="id", columnDefinition="bigint unsigned")
    private long vendor_id;
}
