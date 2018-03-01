package com.unc.securitytest.controllers;

import com.unc.securitytest.services.security.SessionUser;
import com.unc.securitytest.services.security.TokenAuthenticationService;
import com.unc.securitytest.services.security.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {

    private final TokenAuthenticationService authenticationService;
    private final UserSessionService userSessionService;

    @Autowired
    public LoginController(TokenAuthenticationService authenticationService,
                           UserSessionService userSessionService) {
        this.authenticationService = authenticationService;
        this.userSessionService = userSessionService;
    }


    @RequestMapping("/login")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public @ResponseBody String login(@RequestParam String username,
                                      @RequestParam String pass,
                                      HttpServletResponse response) {
//        SessionUser user = (SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SessionUser sessionUser = userSessionService.getUser(username, pass);

        if (sessionUser != null) {
            authenticationService.addAuthentication(response, sessionUser);
        }

        return "true";
    }

    @RequestMapping("/some")
    public @ResponseBody String someEndpoint() {
        return "data";
    }

    @RequestMapping("/admin")
    public @ResponseBody String adminEndpoint() {
        return "admin";
    }
}
