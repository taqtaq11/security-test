package com.unc.securitytest.services.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserSessionService {
    public SessionUser getUser(String username, String pass) {
        //load actual pass from DB
        String actualPass = "qwerty123";

        if (!actualPass.equals(pass)) {
            return null;
        }

        List<GrantedAuthority> roles;

        if (username.equals("jigurda")) {
            roles = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            roles = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new SessionUser(
                "123",
                username,
                pass,
                roles);
    }
}
