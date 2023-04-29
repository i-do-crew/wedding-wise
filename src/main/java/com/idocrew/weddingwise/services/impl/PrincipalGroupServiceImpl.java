package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.PrincipalGroup;
import com.idocrew.weddingwise.entity.User;
import com.idocrew.weddingwise.repositories.PrincipalGroupRepository;
import com.idocrew.weddingwise.services.PrincipalGroupService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service("principalGroupService")
class PrincipalGroupServiceImpl implements PrincipalGroupService {
    private final PrincipalGroupRepository principalGroupRepository;
    private final EntityManager em;

    @Override
    public PrincipalGroup savePrincipalGroup(PrincipalGroup principalGroup) {
        return principalGroupRepository.save(principalGroup);
    }

    @Override
    public PrincipalGroup findByCode(String role) {
        return principalGroupRepository.findByCode(role);
    }

    @Override
    public Set<PrincipalGroup> findByUser(User user) {
        String query = String.format("select g.* from principal_groups g where g.id=(select ug.group_id from user_groups ug where ug.user_id=%d);", user.getId());
        PrincipalGroup group = (PrincipalGroup) em.createNativeQuery(query, PrincipalGroup.class).getSingleResult();
        return Set.of(group);
    }
}
