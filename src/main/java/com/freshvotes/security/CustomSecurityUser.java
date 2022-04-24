package com.freshvotes.security;

import com.freshvotes.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public class CustomSecurityUser implements UserDetails {

   private User user;

    public CustomSecurityUser(User user) {
        this.user = user;
    }

    @Override
    public Set<Authority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
