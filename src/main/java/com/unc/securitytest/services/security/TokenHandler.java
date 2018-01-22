package com.unc.securitytest.services.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class TokenHandler {
    private static final String USER_ID = "user_id";
    private static final String AUTHORITIES = "authorities";


    String createAccessToken(SessionUser user) {
        final Date now = new Date();

        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put(USER_ID, String.valueOf(user.getUserId()));

        String authorities = user.getAuthorities()
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        claims.put(AUTHORITIES, authorities);

        return Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + TimeUnit.HOURS.toMillis(1L)))
                .signWith(SignatureAlgorithm.HS512, "some-random-secret-key")
                .compact();
    }

    SessionUser parseSessionUser(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey("some-random-secret-key")
                .parseClaimsJws(token)
                .getBody();
        SessionUser user = new SessionUser(
                (String) claims.get(USER_ID),
                claims.getSubject(),
                AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get(AUTHORITIES))
        );
//        parseSpecificAccessDetails(user, claims);
        return user;
    }
}
