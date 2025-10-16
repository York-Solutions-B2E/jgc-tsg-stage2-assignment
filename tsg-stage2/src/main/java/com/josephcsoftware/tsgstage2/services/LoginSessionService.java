package com.josephcsoftware.tsgstage2.services;

import com.josephcsoftware.tsgstage2.repositories.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class LoginSessionService {
    
    private final UserRepository userRepository;

    public LoginSessionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
