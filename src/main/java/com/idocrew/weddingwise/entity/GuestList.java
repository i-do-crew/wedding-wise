package com.idocrew.weddingwise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "guest_lists", schema = "weddingwise",
    indexes = {@Index(name = "customer_id", columnList = "customer_id")})
public class GuestList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "fname", nullable = false, length = 50)
    private String fname;

    @Column(name = "lname", nullable = false, length = 50)
    private String lname;

    @Column(name = "plus_one", nullable = false)
    private Boolean plusOne = false;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "ph_number", nullable = false, length = 20)
    private String phNumber;

    @Column(name = "street", nullable = false, length = 50)
    private String street;

    @Column(name = "apt_no", length = 20)
    private String aptNo;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "state", nullable = false, length = 2)
    private String state;

    @Column(name = "zip", nullable = false, length = 10)
    private String zip;

    @Column(name = "rsvp", nullable = false, length = 10)
    private String rsvp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

}
