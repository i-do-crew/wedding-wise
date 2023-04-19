package com.idocrew.weddingwise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "principal_groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "group")
    private String group;
    @Column(name = "description")
    private String description;
    @ManyToMany(mappedBy ="userGroups", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private List<User> users;
}