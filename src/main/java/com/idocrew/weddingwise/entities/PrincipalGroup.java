package com.idocrew.weddingwise.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "principal_groups", schema = "weddingwise")
public class PrincipalGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Column(name = "`group`", nullable = false, length = 20)
    private String group;

    @Column(name = "description", nullable = false, length = 50)
    private String description;

    @OneToMany(mappedBy = "group")
    private Set<UserGroup> userGroups = new LinkedHashSet<>();
}