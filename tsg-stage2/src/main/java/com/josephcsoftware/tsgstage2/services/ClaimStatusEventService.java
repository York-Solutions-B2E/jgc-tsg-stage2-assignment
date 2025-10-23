package com.josephcsoftware.tsgstage2.services;

import java.util.UUID;

import com.josephcsoftware.tsgstage2.models.ClaimStatus;
import com.josephcsoftware.tsgstage2.models.ClaimStatusEvent;
import com.josephcsoftware.tsgstage2.repositories.ClaimStatusEventRepository;

import org.springframework.stereotype.Service;

@Service
public class ClaimStatusEventService {
    
    private final ClaimStatusEventRepository claimStatusEventRepository;

    public ClaimStatusEventService(ClaimStatusEventRepository claimStatusEventRepository) {
        this.claimStatusEventRepository = claimStatusEventRepository;
    }

    public ClaimStatusEvent createEvent(UUID claimId, ClaimStatus status) {
        ClaimStatusEvent newEvent = new ClaimStatusEvent();
        newEvent.setClaimId(claimId);
        newEvent.setStatus(status);
        claimStatusEventRepository.save(newEvent);
        return newEvent;
    }
}
