package com.idocrew.weddingwise.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "idea_board",
    schema = "weddingwise",
    indexes = {@Index(name = "customer_id", columnList = "customer_id")})
public class IdeaBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "filepath", nullable = false, length = 100)
    private String filepath;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

}
