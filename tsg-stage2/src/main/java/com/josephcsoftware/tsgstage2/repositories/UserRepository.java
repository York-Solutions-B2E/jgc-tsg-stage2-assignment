package com.josephcsoftware.tsgstage2.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.josephcsoftware.tsgstage2.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByEmailAndAuthSubAndAuthProvider(String email, String authSub, String authProvider);

    Optional<User> findByAuthSub(String authSub);
}
