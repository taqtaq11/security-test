package com.unc.securitytest.services.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthentication implements Authentication {
    private final SessionUser sessionUser;

    public UserAuthentication(SessionUser user) {
        sessionUser = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return sessionUser.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return sessionUser;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return sessionUser.getUsername();
    }
}
