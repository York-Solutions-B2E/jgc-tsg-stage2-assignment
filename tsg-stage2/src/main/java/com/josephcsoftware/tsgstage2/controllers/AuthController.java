package com.josephcsoftware.tsgstage2.controllers;

import com.josephcsoftware.tsgstage2.SimpleSession;
import com.josephcsoftware.tsgstage2.models.User;
import com.josephcsoftware.tsgstage2.services.AuthService;
import com.josephcsoftware.tsgstage2.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@CrossOrigin(origins = "${jgc.cors.origin}")
@RequestMapping("${jgc.project.root}/")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // This JWT is the authorization token from Google
    @GetMapping("login")
    public ResponseEntity<Void> validateLogin(@AuthenticationPrincipal Jwt jwt) {
        // Pass the JWT to authService to check for the existence of a user; if none, make one
        User foundLogin = authService.findUserByJWT(jwt);

        if (foundLogin == null) {
            // Something went very wrong, so report back
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
