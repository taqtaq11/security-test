package com.unc.securitytest.services.security;

import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class TokenAuthenticationService {

    private final TokenHandler tokenHandler;

    @Autowired
    public TokenAuthenticationService(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    public void addAuthentication(HttpServletResponse response, SessionUser user) {
        Cookie cookie = new Cookie("access_token", tokenHandler.createAccessToken(user));
        cookie.setPath("/");
//        cookie.setSecure(request.isSecure());
        response.addCookie(cookie);
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        Cookie tokenCookie = WebUtils.getCookie(request, "access_token");
        String accessToken;
        if (tokenCookie != null && !(accessToken = tokenCookie.getValue()).isEmpty()) {
            try {
                SessionUser user = tokenHandler.parseSessionUser(accessToken);
                return new UserAuthentication(user);
            } catch (JwtException ex) {
                return null;
            }
        }
        return null;
    }

    //can be used to update expired tokens
//    private Authentication updateToken(Claims claims, HttpServletResponse response) {
//        try {
//            SessionUser user = databaseUserDetailsService.getUser(claims.getSubject());
//            addAuthentication(response, user);
//            return new UserAuthentication(user);
//        } catch (JwtException e) {
//            return null;
//        }
//    }
}
