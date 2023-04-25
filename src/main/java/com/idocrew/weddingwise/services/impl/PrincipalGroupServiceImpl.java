package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.PrincipalGroup;
import com.idocrew.weddingwise.repositories.PrincipalGroupRepository;
import com.idocrew.weddingwise.services.PrincipalGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("principalGroupService")
class PrincipalGroupServiceImpl implements PrincipalGroupService {
    private final PrincipalGroupRepository principalGroupRepository;
    @Override
    public PrincipalGroup savePrincipalGroup(PrincipalGroup principalGroup) {
        return principalGroupRepository.save(principalGroup);
    }

    @Override
    public PrincipalGroup findByCode(String role) {
        return principalGroupRepository.findByCode(role);
    }
}
