package com.idocrew.weddingwise.controllers;

import com.idocrew.weddingwise.controllers.Models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="customers")
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="budget", columnDefinition="int(11)")
    private int budget;
    @Column(name="guest_count", columnDefinition="int(11)")
    private int guestCount;
    @Column(name="city_state", columnDefinition="varchar(25)")
    private String cityState;
    @Column(name="partner_fname", columnDefinition="varchar(50)")
    private String partnerFName;
    @Column(name="partner_lname", columnDefinition="varchar(50)")
    private String partnerLName;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", columnDefinition = "bigint(20)")
    private User user;
}
