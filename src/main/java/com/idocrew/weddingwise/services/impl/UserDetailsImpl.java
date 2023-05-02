package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.PrincipalGroup;
import com.idocrew.weddingwise.entity.User;
import com.idocrew.weddingwise.entity.UserWithRoles;
import com.idocrew.weddingwise.services.PrincipalGroupService;
import com.idocrew.weddingwise.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserDetailsImpl implements UserDetailsService {
    private final UserService userService;
    PrincipalGroupService principalGroupService;

    public UserDetailsImpl(UserService userService, PrincipalGroupService principalGroupService) {
        this.userService = userService;
        this.principalGroupService = principalGroupService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userService.findByUsername(username);
        Set<PrincipalGroup> groups = principalGroupService.findByUser(user);
        user.setUserGroups(groups);

        //TODO: Uncomment once verification processing is complete
        user.setLoginDisabled(!user.isAccountVerified());

        return new UserWithRoles(user);
    }
}


