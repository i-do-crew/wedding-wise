package com.idocrew.weddingwise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="customers", schema = "weddingwise")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name="budget", columnDefinition="int(11)")
    private int budget;
    @Column(name="guest_count", columnDefinition="int(11)")
    private int guestCount;
    @Column(name="partner_fname", columnDefinition="varchar(50)", nullable = false)
    private String partnerFName;
    @Column(name="partner_lname", columnDefinition="varchar(50)", nullable = false)
    private String partnerLName;
    @Column(name="wedding_dt", columnDefinition="date")
    private LocalDate weddingDt;
    @OneToMany(mappedBy = "customer")
    private Set<GuestList> guestLists = new LinkedHashSet<>();
    @OneToMany(mappedBy = "customer")
    private Set<IdeaBoard> ideaBoards = new LinkedHashSet<>();
    @OneToMany(mappedBy = "customer")
    private Set<VendorRatingsReview> vendorRatingsReviews = new LinkedHashSet<>();
    @OneToMany(mappedBy = "customer")
    private Set<CustomerVendor> customerVendors = new LinkedHashSet<>();
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", columnDefinition = "bigint(20)", nullable = false)
    private User user;
}
