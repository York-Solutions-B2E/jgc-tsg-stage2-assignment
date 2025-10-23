package com.josephcsoftware.tsgstage2.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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

    private final ClaimLineService claimLineService;
    private final ClaimStatusEventService claimStatusEventService;

    public ClaimService(
                        ClaimRepository claimRepository,
                        ClaimLineService claimLineService,
                        ClaimStatusEventService claimStatusEventService
                        ) {
        this.claimRepository = claimRepository;
        this.claimLineService = claimLineService;
        this.claimStatusEventService = claimStatusEventService;
    }

    public Claim createClaim(UUID memberId, UUID providerId, String[] reasons) {
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

        ArrayList<ClaimStatusEvent> history = new ArrayList<ClaimStatusEvent>();
        ArrayList<ClaimLine> lines = new ArrayList<ClaimLine>();

        // Save now, to let auto-gen fields to populate
        claimRepository.save(newClaim);

        UUID myId = newClaim.getId();

        // Set up history field
        history.add(claimStatusEventService.createEvent(myId, ClaimStatus.SUBMITTED));
        history.add(claimStatusEventService.createEvent(myId, ClaimStatus.IN_REVIEW));
        history.add(claimStatusEventService.createEvent(myId, ClaimStatus.PROCESSED));
        history.add(claimStatusEventService.createEvent(myId, ClaimStatus.PAID));

        // Set up claim lines
        BigDecimal totalBilled = BigDecimal.valueOf(0, 2);
        BigDecimal totalAllowed = totalBilled;
        BigDecimal totalPlanPaid = totalBilled;
        BigDecimal totalMemberResponsibility = totalBilled;
        for (int i = 0; i < reasons.length; i++) {
            ClaimLine line = claimLineService.createLine(
                                                         i + 1,
                                                         myId,
                                                         reasons[i]
                                                         );
            lines.add(line);

            // Add to totals
            totalBilled = totalBilled.add(line.getBilledAmount());
            totalAllowed = totalAllowed.add(line.getAllowedAmount());
            totalPlanPaid = totalPlanPaid.add(line.getPlanPaid());
            totalMemberResponsibility = totalMemberResponsibility.add(line.getMemberResponsibility());
        }
        
        // Save lists
        newClaim.setStatusHistory(history);
        newClaim.setLines(lines);

        // Write to database
        claimRepository.save(newClaim);

        return newClaim;
    }
}
