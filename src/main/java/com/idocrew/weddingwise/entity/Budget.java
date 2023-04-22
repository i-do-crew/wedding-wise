package com.idocrew.weddingwise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name="amount", columnDefinition="decimal(19,2)")
    private int amount;

    @OneToOne(mappedBy = "budget")
    private Customer customer;

    @OneToMany(mappedBy = "budget")
    private Set<Vendor> vendors = new LinkedHashSet<>();
}
