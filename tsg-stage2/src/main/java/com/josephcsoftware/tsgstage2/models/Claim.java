package com.josephcsoftware.tsgstage2.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "claims")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID", nullable = false, unique = true)
    private UUID id;

    // Some sort of human-readable key for UI
    @Column(nullable = false, unique = true)
    private String claimNumber;

    @Column(columnDefinition = "UUID", nullable = false)
    private UUID memberId;

    @Column(columnDefinition = "UUID", nullable = false)
    private UUID providerId;

    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate serviceStartDate;

    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate serviceEndDate;

    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate receivedDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15)", nullable = false, length = 15)
    private NetworkTier status;

    // 000.00
    @Column(columnDefinition = "NUMERIC(23,2)",
            nullable = false, precision = 23, scale = 2)
    private BigDecimal totalBilled;
    
    // 000.00
    @Column(columnDefinition = "NUMERIC(23,2)",
            nullable = false, precision = 23, scale = 2)
    private BigDecimal totalAllowed;
    
    // 000.00
    @Column(columnDefinition = "NUMERIC(23,2)",
            nullable = false, precision = 23, scale = 2)
    private BigDecimal totalPlanPaid;
    
    // 000.00
    @Column(columnDefinition = "NUMERIC(23,2)",
            nullable = false, precision = 23, scale = 2)
    private BigDecimal totalMemberResponsibility;

    @OneToMany
    @Column
    private List<ClaimLine> lines;

    @OneToMany
    @Column
    private List<ClaimStatusEvent> statusHistory;
    
    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}
