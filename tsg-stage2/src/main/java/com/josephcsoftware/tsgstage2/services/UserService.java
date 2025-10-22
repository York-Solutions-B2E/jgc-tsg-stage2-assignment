package com.josephcsoftware.tsgstage2.services;

import java.util.List;

import com.josephcsoftware.tsgstage2.SimpleSession;
import com.josephcsoftware.tsgstage2.models.User;
import com.josephcsoftware.tsgstage2.repositories.UserRepository;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Tries to retrieve a user.
    // If this fails, then a new one is created.
    public User findUserBySession(SimpleSession session) {
        List<User> userList = userRepository
            .findByEmailAndAuthSubAndAuthProvider(
                                                  session.getEmail(),
                                                  session.getSubject(),
                                                  session.getProvider()
                                                  );

        if (userList.size() > 0) { // SHOULD be 1, but we're not picky
            User foundUser = userList.get(0);
            return foundUser;
        }

        // None found; create a new one
        User newUser = new User();
        newUser.setAuthSub(session.getSubject());
        newUser.setAuthProvider(session.getProvider());
        newUser.setEmail(session.getEmail());
        userRepository.save(newUser);

        return null;
    }

    // If a user has an account, then do nothing
    // If a user has no account, then make one from the JWT
    public User findUserByJWT(Jwt jwt) {
        try {
            SimpleSession session = new SimpleSession(jwt);

            User user = this.findUserBySession(session);
            if (user == null) {
                System.out.println("Failed to find user: " + session.getEmail());
                System.out.println("                     " + session.getProvider());
                System.out.println("                     " + session.getSubject());
                return null;
            }
        
            return user;
        } catch (Exception ex) {
            // SimpleSession creation already has some error handling,
            // so if we still get an error here, then something has gone
            // VERY wrong.
            System.out.println(ex);
            return null;
        }
    }
}
