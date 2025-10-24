package com.josephcsoftware.tsgstage2.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.josephcsoftware.tsgstage2.models.Address;
import com.josephcsoftware.tsgstage2.models.Claim;
import com.josephcsoftware.tsgstage2.models.ClaimLine;
import com.josephcsoftware.tsgstage2.models.ClaimStatus;
import com.josephcsoftware.tsgstage2.models.ClaimStatusEvent;
import com.josephcsoftware.tsgstage2.models.Provider;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimDTO {

    private String claimNumber;
    private String providerName;
    private String providerSpecialty;
    private Address providerAddress;
    private String providerPhone;
    private LocalDate serviceStartDate;
    private LocalDate serviceEndDate;
    private LocalDate receivedDate;
    private String status;
    private BigDecimal totalBilled;
    private BigDecimal totalAllowed;
    private BigDecimal totalPlanPaid;
    private BigDecimal totalMemberResponsibility;
    private List<ClaimLineDTO> lines;
    private List<ClaimStatusEventDTO> statusHistory;
    private OffsetDateTime updatedAt;

    public static ClaimDTO from(Claim claim, Provider provider) {
        ClaimDTO dto = new ClaimDTO();

        dto.setClaimNumber(claim.getClaimNumber());
        dto.setProviderName(provider.getName());
        dto.setProviderSpecialty(provider.getSpecialty());
        dto.setProviderAddress(provider.getAddress());
        dto.setProviderPhone(provider.getPhone());
        dto.setServiceStartDate(claim.getServiceStartDate());
        dto.setServiceEndDate(claim.getServiceEndDate());
        dto.setReceivedDate(claim.getReceivedDate());
        dto.setStatus(statusToString(claim.getStatus()));
        dto.setTotalBilled(claim.getTotalBilled());
        dto.setTotalAllowed(claim.getTotalAllowed());
        dto.setTotalPlanPaid(claim.getTotalPlanPaid());
        dto.setTotalMemberResponsibility(claim.getTotalMemberResponsibility());
        dto.setUpdatedAt(claim.getUpdatedAt());

        ArrayList<ClaimLineDTO> lines = new ArrayList<ClaimLineDTO>();
        List<ClaimLine> srcLines = claim.getLines();
        for (int i = 0; i < srcLines.size(); i++) {
            lines.add(ClaimLineDTO.from(srcLines.get(i)));
        }
        dto.setLines(lines);

        ArrayList<ClaimStatusEventDTO> statusHistory = new ArrayList<ClaimStatusEventDTO>();
        List<ClaimStatusEvent> srcHistory = claim.getStatusHistory();
        for (int i = 0; i < srcHistory.size(); i++) {
            statusHistory.add(ClaimStatusEventDTO.from(srcHistory.get(i)));
        }
        dto.setStatusHistory(statusHistory);

        return dto;
    }

    public static String statusToString(ClaimStatus status) {
        switch (status) {
        case ClaimStatus.IN_REVIEW:
            return "in review";
        case ClaimStatus.PROCESSED:
            return "processed";
        case ClaimStatus.PAID:
            return "paid";
        case ClaimStatus.DENIED:
            return "denied";
        }

        return "submitted";
    }
}
