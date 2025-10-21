package com.josephcsoftware.tsgstage2.services;

import com.josephcsoftware.tsgstage2.SimpleSession;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private UserService userService;

    public AuthService() { }

    // If a user has an account, then do nothing
    // If a user has no account, then make one from the JWT
    public SimpleSession verifyLogin(Jwt jwt) {
        try {
            SimpleSession session = new SimpleSession(jwt);

            userService.ensureExistenceOfUser(session);
        
            return session;
        } catch (Exception ex) {
            // SimpleSession creation already has some error handling,
            // so if we still get an error here, then something has gone
            // VERY wrong.
            System.out.println(ex);
            return null;
        }
    }
}
