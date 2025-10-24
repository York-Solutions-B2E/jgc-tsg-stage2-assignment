package com.josephcsoftware.tsgstage2.repositories;

import java.util.List;
import java.util.UUID;

import com.josephcsoftware.tsgstage2.models.Claim;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepository extends JpaRepository<Claim, UUID> {
    List<Claim> findClaimByMemberId(UUID memberId);
}
