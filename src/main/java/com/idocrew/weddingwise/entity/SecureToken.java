package com.idocrew.weddingwise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "secure_tokens", schema = "weddingwise")
public class SecureToken{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String token;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @Column(updatable = false)
    @Basic(optional = false)
    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName ="id")
    private User user;

    @Transient
    private boolean isExpired;

    public boolean isExpired() {
        return getExpiresAt().isBefore(LocalDateTime.now()); // this is generic implementation, you can always make it timezone specific
    }
}
