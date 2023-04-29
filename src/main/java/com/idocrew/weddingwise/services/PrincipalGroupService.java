package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.PrincipalGroup;
import com.idocrew.weddingwise.entity.User;

import java.util.Set;

public interface PrincipalGroupService {
    PrincipalGroup savePrincipalGroup(PrincipalGroup principalGroup);
    PrincipalGroup findByCode(String role);
    Set<PrincipalGroup> findByUser(User user);
}
