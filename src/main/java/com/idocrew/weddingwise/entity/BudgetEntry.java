package com.idocrew.weddingwise.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;

import java.math.BigDecimal;

@Getter
@Setter
@Scope("session")
@Entity
@Table(name="budget_entries", schema="weddingwise")
public class BudgetEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @Min(value = 0, message = "Amount cannot be less than 0")
    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
}
