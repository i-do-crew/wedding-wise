package com.idocrew.weddingwise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users", schema = "weddingwise",
    indexes = {
        @Index(name = "email", columnList = "email", unique = true),
        @Index(name = "username", columnList = "username", unique = true)})
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
        userGroups = copy.userGroups;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "username", columnDefinition = "varchar(50)", nullable = false)
    private String username;

    @Column(name = "email", columnDefinition = "varchar(50)", nullable = false)
    private String email;

    @Column(name = "password", columnDefinition = "varchar(100)", nullable = false)
    private String password;

    @Column(name = "first_name", columnDefinition = "varchar(25) not null", nullable = false)
    private String firstName;

    @Column(name = "last_name", columnDefinition = "varchar(25) not null", nullable = false)
    private String lastName;

    @Column(name = "city", columnDefinition = "varchar(25)", nullable = false)
    private String city;

    @Column(name = "state", columnDefinition = "varchar(25)", nullable = false)
    private String state;

    @Column(name = "account_verified", columnDefinition = "bit", nullable = false)
    private boolean accountVerified = false;

    @Column(name = "failed_login_attempts", columnDefinition = "int 11", nullable = false)
    private int failedLoginAttempts;

    @Column(name = "login_disabled", columnDefinition = "bit", nullable = false)
    private boolean loginDisabled = false;

    @Column(name = "account_non_expired", columnDefinition = "bit", nullable = false)
    private boolean accountNonExpired = true;

    @Column(name = "account_non_locked", columnDefinition = "bit", nullable = false)
    private boolean accountNonLocked = true;

    @Column(name = "credentials_non_expired", columnDefinition = "bit", nullable = false)
    private boolean credentialsNonExpired = true;

    @OneToMany(mappedBy = "user")
    private Set<SecureToken> tokens;

    @OneToMany(mappedBy = "user")
    private Set<Vendor> vendors = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Customer> customers = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(
            name = "user_groups",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false))
    private Set<PrincipalGroup> userGroups = new LinkedHashSet<>();

    public void addGroup(PrincipalGroup group) {
        userGroups.add(group);
    }
}
