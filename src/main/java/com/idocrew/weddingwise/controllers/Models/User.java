package com.idocrew.weddingwise.controllers.Models;

import jakarta.persistence.*;

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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public boolean isAccountVerified() {
        return accountVerified;
    }

    public void setAccountVerified(boolean accountVerified) {
        this.accountVerified = accountVerified;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public boolean isLoginDisabled() {
        return loginDisabled;
    }

    public void setLoginDisabled(boolean loginDisabled) {
        this.loginDisabled = loginDisabled;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public User(){
    }

    public User(User copy) {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
