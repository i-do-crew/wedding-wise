package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.PrincipalGroup;

public interface PrincipalGroupService {
    PrincipalGroup savePrincipalGroup(PrincipalGroup principalGroup);
    PrincipalGroup findByCode(String role);
}
