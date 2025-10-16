package com.josephcsoftware.tsgstage2.controllers;

import com.josephcsoftware.tsgstage2.services.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getHelloWorld() {
        return "Hello world!";
    }
    
}
