package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.User;
import com.idocrew.weddingwise.entity.UserWithRoles;
import com.idocrew.weddingwise.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsLoader implements UserDetailsService {
    private final UserRepository users;

    public UserDetailsLoader(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("No user found for " + username);
        }

        //TODO: Uncomment once verification processing is complete
        //user.setLoginDisabled(!user.isAccountVerified());

        return new UserWithRoles(user);
    }
}


