package com.josephcsoftware.tsgstage2.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.josephcsoftware.tsgstage2.Utils;
import com.josephcsoftware.tsgstage2.models.Claim;
import com.josephcsoftware.tsgstage2.models.ClaimLine;
import com.josephcsoftware.tsgstage2.models.ClaimStatus;
import com.josephcsoftware.tsgstage2.models.ClaimStatusEvent;
import com.josephcsoftware.tsgstage2.repositories.ClaimRepository;

import org.springframework.stereotype.Service;

@Service
public class ClaimService {
    
    private final ClaimRepository claimRepository;

    private final ClaimStatusEventService claimStatusEventService;

    public ClaimService(
                        ClaimRepository claimRepository,
                        ClaimStatusEventService claimStatusEventService
                        ) {
        this.claimRepository = claimRepository;
        this.claimStatusEventService = claimStatusEventService;
    }

    public Claim beginClaim(UUID memberId, UUID providerId) {
        Claim newClaim = new Claim();

        newClaim.setClaimNumber("#C-" + Utils.randomCode());
        newClaim.setMemberId(memberId);
        newClaim.setProviderId(providerId);

        LocalDate occurrence = Utils.randomDateBetween(
                                                       Utils.randomInYear(Utils.START_YEAR),
                                                       LocalDate.now()
                                                       );
        LocalDate reviewed = occurrence.plusDays(1);
        LocalDate processed = occurrence.plusDays(2);
        LocalDate received = occurrence.plusDays(3);
        
        newClaim.setServiceStartDate(occurrence);
        newClaim.setServiceEndDate(occurrence);
        newClaim.setReceivedDate(received);
        newClaim.setStatus(ClaimStatus.PAID);

        List<ClaimStatusEvent> history = new List<ClaimStatusEvent>();
        List<ClaimLine> lines = new List<ClaimLine>();

        // We will finish the other details in endClaim()
        claimRepository.save(newClaim);

        UUID myId = newClaim.getId();

        history.add(claimStatusEventService.createEvent(myId, ClaimStatus.SUBMITTED));
        history.add(claimStatusEventService.createEvent(myId, ClaimStatus.IN_REVIEW));
        history.add(claimStatusEventService.createEvent(myId, ClaimStatus.PROCESSED));
        history.add(claimStatusEventService.createEvent(myId, ClaimStatus.PAID));

        // Save lists
        newClaim.setStatusHistory(history);
        newClaim.setLines(lines);
        claimRepository.save(newClaim);

        return newClaim;
    }
}
