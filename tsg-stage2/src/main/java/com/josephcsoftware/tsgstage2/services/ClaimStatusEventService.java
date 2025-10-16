package com.josephcsoftware.tsgstage2.services;

import com.josephcsoftware.tsgstage2.repositories.ClaimStatusEventRepository;

import org.springframework.stereotype.Service;

@Service
public class ClaimStatusEventService {
    
    private final ClaimStatusEventRepository claimStatusEventRepository;

    public ClaimStatusEventService(ClaimStatusEventRepository claimStatusEventRepository) {
        this.claimStatusEventRepository = claimStatusEventRepository;
    }
}
