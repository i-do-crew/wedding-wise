package com.idocrew.weddingwise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "budget_entries")
public class SimpleBudgetEntry {
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    @JoinColumn(name = "customer_id", nullable = false)
    private long customerId;

    @JoinColumn(name = "vendor_id", nullable = false)
    private long vendorId;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
}
