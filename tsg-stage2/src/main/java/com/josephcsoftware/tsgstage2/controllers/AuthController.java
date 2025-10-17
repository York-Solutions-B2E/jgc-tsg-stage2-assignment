package com.josephcsoftware.tsgstage2.controllers;

import com.josephcsoftware.tsgstage2.services.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/auth/me")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<Void> validateLogin(@AuthenticationPrincipal Jwt jwt) {
        // Pass the JWT to authService to check for the existence of a user; if none, make one
        return ResponseEntity.ok().build();
    }
}
