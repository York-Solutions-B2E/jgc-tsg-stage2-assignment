package com.josephcsoftware.tsgstage2.services;

import com.josephcsoftware.tsgstage2.repositories.ClaimRepository;

import org.springframework.stereotype.Service;

@Service
public class ClaimService {
    
    private final ClaimRepository claimRepository;

    public ClaimService(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }
}
