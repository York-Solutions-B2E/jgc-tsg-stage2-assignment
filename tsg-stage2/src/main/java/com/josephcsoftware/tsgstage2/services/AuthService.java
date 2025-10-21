package com.josephcsoftware.tsgstage2.services;

import com.josephcsoftware.tsgstage2.SimpleSession;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    //

    public AuthService() { }

    // If a user has an account, then do nothing
    // If a user has no account, then make one from the JWT
    public SimpleSession verifyLogin(Jwt jwt) {
        SimpleSession session = new SimpleSession(jwt);

        //TODO
        
        return session;
    }
}
