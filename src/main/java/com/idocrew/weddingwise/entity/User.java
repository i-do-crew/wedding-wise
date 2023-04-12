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
@Table(name="users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="username", columnDefinition="varchar(50)")
    private String username;
    @Column(name="email", columnDefinition="varchar(50)")
    private String email;
    @Column(name="password", columnDefinition="varchar(100)")
    private String password;
    @Column(name="role_id", columnDefinition="int(10) unsigned default 1 null")
    private int roleId;
    @Column(name="first_name", columnDefinition="varchar(25) not null")
    private String firstName;
    @Column(name="last_name", columnDefinition="varchar(25) not null")
    private String lastName;
    @Column(name="account_verified", columnDefinition = "bit")
    private boolean accountVerified;
    @Column(name="failed_login_attempts", columnDefinition = "int 11")
    private int failedLoginAttempts;
    @Column(name="login_disabled", columnDefinition = "bit")
    private boolean loginDisabled;
    @Column(name="account_non_expired", columnDefinition = "bit")
    private boolean accountNonExpired;
    @Column(name="account_non_locked", columnDefinition = "bit")
    private boolean accountNonLocked;
    @Column(name="credentials_non_expired", columnDefinition = "bit")
    private boolean credentialsNonExpired;


}
