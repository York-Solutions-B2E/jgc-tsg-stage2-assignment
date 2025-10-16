package com.josephcsoftware.tsgstage2.repositories;

import java.util.UUID;

import com.josephcsoftware.tsgstage2.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
    //
}
