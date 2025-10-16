package com.josephcsoftware.tsgstage2.repositories;

import java.util.UUID;

import com.josephcsoftware.tsgstage2.models.ClaimLine;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimLineRepository extends JpaRepository<ClaimLine, UUID> {
    //
}
