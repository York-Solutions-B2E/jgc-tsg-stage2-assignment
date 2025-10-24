package com.josephcsoftware.tsgstage2.dtos;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.josephcsoftware.tsgstage2.models.ClaimStatusEvent;

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
public class ClaimStatusEventDTO {

    private String status;
    private OffsetDateTime occurredAt;
    private String note;

    public static ClaimStatusEventDTO from(ClaimStatusEvent event) {
        ClaimStatusEventDTO dto = new ClaimStatusEventDTO();

        dto.setStatus(ClaimDTO.statusToString(event.getStatus()));
        dto.setOccurredAt(event.getOccurredAt());
        dto.setNote(event.getNote());

        return dto;
    }
}
