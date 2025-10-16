package com.josephcsoftware.tsgstage2.services;

import com.josephcsoftware.tsgstage2.repositories.ClaimLineRepository;

import org.springframework.stereotype.Service;

@Service
public class ClaimLineService {
    
    private final ClaimLineRepository claimLineRepository;

    public ClaimLineService(ClaimLineRepository claimLineRepository) {
        this.claimLineRepository = claimLineRepository;
    }
}
