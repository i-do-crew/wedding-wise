package com.idocrew.weddingwise.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users", schema = "weddingwise", indexes = {
        @Index(name = "email", columnList = "email", unique = true),
        @Index(name = "username", columnList = "username", unique = true)
})
public class User {

    public User (User copy) {
        id = copy.id;
        username = copy.username;
        email = copy.email;
        password = copy.password;
        firstName = copy.firstName;
        lastName = copy.lastName;
        city = copy.city;
        state = copy.state;
        accountVerified = copy.accountVerified;
        failedLoginAttempts = copy.failedLoginAttempts;
        loginDisabled = copy.loginDisabled;
        accountNonExpired = copy.accountNonExpired;
        accountNonLocked = copy.accountNonLocked;
        credentialsNonExpired = copy.credentialsNonExpired;
        vendors = copy.vendors;
        customers = copy.customers;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name="username", columnDefinition="varchar(50)", nullable = false)
    private String username;

    @Column(name="email", columnDefinition="varchar(50)", nullable = false)
    private String email;

    @Column(name="password", columnDefinition="varchar(100)", nullable = false)
    private String password;

    @Column(name="first_name", columnDefinition="varchar(25) not null", nullable = false)
    private String firstName;

    @Column(name="last_name", columnDefinition="varchar(25) not null", nullable = false)
    private String lastName;

    @Column(name="city", columnDefinition="varchar(25)", nullable = false)
    private String city;

    @Column(name="state", columnDefinition="varchar(25)", nullable = false)
    private String state;

    @Column(name="account_verified", columnDefinition = "bit", nullable = false)
    private boolean accountVerified = false;

    @Column(name="failed_login_attempts", columnDefinition = "int 11", nullable = false)
    private int failedLoginAttempts;

    @Column(name="login_disabled", columnDefinition = "bit", nullable = false)
    private boolean loginDisabled = false;

    @Column(name="account_non_expired", columnDefinition = "bit", nullable = false)
    private boolean accountNonExpired = false;

    @Column(name="account_non_locked", columnDefinition = "bit", nullable = false)
    private boolean accountNonLocked = false;

    @Column(name="credentials_non_expired", columnDefinition = "bit", nullable = false)
    private boolean credentialsNonExpired = false;

    @OneToMany(mappedBy = "vendor")
    private Set<VendorsMusicGenre> vendorsMusicGenres = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Vendor> vendors = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Customer> customers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vendor")
    private Set<VendorPackage> vendorPackages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<UserGroup> userGroups = new LinkedHashSet<>();

}
