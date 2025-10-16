package com.josephcsoftware.tsgstage2.repositories;

import java.util.UUID;

import com.josephcsoftware.tsgstage2.models.ClaimStatusEvent;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimStatusEventRepository extends JpaRepository<ClaimStatusEvent, UUID> {
    //
}
