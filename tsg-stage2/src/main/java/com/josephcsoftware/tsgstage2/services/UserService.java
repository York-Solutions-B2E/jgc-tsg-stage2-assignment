package com.josephcsoftware.tsgstage2.services;

import com.josephcsoftware.tsgstage2.SimpleSession;
import com.josephcsoftware.tsgstage2.repositories.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void ensureExistenceOfUser(SimpleSession session) {
        //TODO
    }
}
