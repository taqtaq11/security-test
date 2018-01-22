package com.unc.securitytest.services.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SessionUser implements UserDetails {
    private final String userId;
    private final String username;
    private final String pass;
    private final List<GrantedAuthority> grantedAuthorities;

    public SessionUser(String userId, String username, List<GrantedAuthority> grantedAuthorities) {
        this(userId, username, "", grantedAuthorities);
    }

    public SessionUser(String userId, String username, String pass, List<GrantedAuthority> grantedAuthorities) {
        this.userId = userId;
        this.username = username;
        this.pass = pass;
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return pass;
    }

    @Override
    public String getUsername() {
        return username;
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

    public String getUserId() {
        return userId;
    }
}
