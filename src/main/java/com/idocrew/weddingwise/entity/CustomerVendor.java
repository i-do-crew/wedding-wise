package com.idocrew.weddingwise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customer_vendors",
       schema = "weddingwise",
       indexes = {
        @Index(name = "vendor_id", columnList = "vendor_id"),
        @Index(name = "customer_id", columnList = "customer_id")})
public class CustomerVendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "favorited")
    private Boolean favorited;

    @Column(name = "amount_quoted", nullable = false)
    private Integer amountQuoted;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

}