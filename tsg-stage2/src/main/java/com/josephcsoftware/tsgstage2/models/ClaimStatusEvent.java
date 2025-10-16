package com.josephcsoftware.tsgstage2.models;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "claim_status_event")
public class ClaimStatusEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID", nullable = false, unique = true)
    private UUID id;

    @Column(columnDefinition = "UUID", nullable = false)
    private UUID claimId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15)", nullable = false, length = 15)
    private ClaimStatus status;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private OffsetDateTime occurredAt;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String note;
}
