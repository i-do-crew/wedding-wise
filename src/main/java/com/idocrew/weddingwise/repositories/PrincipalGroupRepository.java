package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entities.PrincipalGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrincipalGroupRepository extends JpaRepository<PrincipalGroup, Long> {
    PrincipalGroup findByCode(String code);
}
