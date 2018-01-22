package com.unc.securitytest.controllers;

import com.unc.securitytest.services.security.DatabaseUserDetailsService;
import com.unc.securitytest.services.security.SessionUser;
import com.unc.securitytest.services.security.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {

    private final TokenAuthenticationService authenticationService;
    private final DatabaseUserDetailsService databaseUserDetailsService;

    @Autowired
    public LoginController(TokenAuthenticationService authenticationService,
                           DatabaseUserDetailsService databaseUserDetailsService) {
        this.authenticationService = authenticationService;
        this.databaseUserDetailsService = databaseUserDetailsService;
    }


    @RequestMapping("/login")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public String login(@RequestParam String username,
                        @RequestParam String pass,
                        HttpServletResponse response) {
//        SessionUser user = (SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SessionUser sessionUser = databaseUserDetailsService.getUser(username, pass);

        if (sessionUser != null) {
            authenticationService.addAuthentication(response, sessionUser);
        }

        response.addCookie(new Cookie("test", "test1"));

        return "true";
    }

    @RequestMapping("/some")
    public String someEndpoint() {
        return "data";
    }
}
