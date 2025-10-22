package com.josephcsoftware.tsgstage2.services;

import com.josephcsoftware.tsgstage2.models.User;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public User findUserByJWT(Jwt jwt) {
        return userService.findUserByJWT(jwt);
    }
}
