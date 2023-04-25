package com.idocrew.weddingwise.entity;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class UserWithRoles extends User implements UserDetails {

    public UserWithRoles(User user) {
        super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<PrincipalGroup> userGroups = this.getUserGroups();
        List<String> list = new ArrayList<>();
        for (PrincipalGroup principalGroup : userGroups) {
            list.add("ROLE_" + principalGroup.getCode());
        }
        String roles = StringUtils.join(list, ',');
        return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
    }

    @Override
    public boolean isAccountNonExpired() {
        return super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return super.isAccountVerified() && !super.isLoginDisabled();
    }
}

