package com.unc.securitytest.services.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserSessionService {
    public SessionUser getUser(String username, String pass) {
        //load actual pass from DB
        String actualPass = "qwerty123";

        if (!actualPass.equals(pass)) {
            return null;
        }

        return new SessionUser(
                "123",
                username,
                pass,
                Collections.singletonList(new SimpleGrantedAuthority("user")));
    }
}
