package com.josephcsoftware.tsgstage2.models;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
@Table(name = "claim_lines")
public class ClaimLine {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID", name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(columnDefinition = "UUID", name = "claim_id", nullable = false)
    private UUID claimId;

    @Size(min = 1)
    @Column(columnDefinition = "INTEGER", name = "line_number", nullable = false)
    private Integer lineNumber;

    // A shorthand code for conditions and treatments
    @Column(columnDefinition = "TEXT", name = "cpt_code", nullable = false)
    private String cptCode;

    @Column(columnDefinition = "TEXT", name = "description", nullable = false)
    private String description;
    
    // 000.00
    @Column(columnDefinition = "NUMERIC(23,2)", name = "billed_amount",
            nullable = false, precision = 23, scale = 2)
    private BigDecimal billedAmount;

    // 000.00
    @Column(columnDefinition = "NUMERIC(23,2)", name = "allowed_amount",
            nullable = false, precision = 23, scale = 2)
    private BigDecimal allowedAmount;

    // 000.00
    @Column(columnDefinition = "NUMERIC(23,2)", name = "deductible_applied",
            nullable = false, precision = 23, scale = 2)
    private BigDecimal deductibleApplied;

    // 000.00
    @Column(columnDefinition = "NUMERIC(23,2)", name = "copay_applied",
            nullable = false, precision = 23, scale = 2)
    private BigDecimal copayApplied;

    // 000.00
    @Column(columnDefinition = "NUMERIC(23,2)", name = "coinsurance_applied",
            nullable = false, precision = 23, scale = 2)
    private BigDecimal coinsuranceApplied;

    // 000.00
    @Column(columnDefinition = "NUMERIC(23,2)", name = "plan_paid",
            nullable = false, precision = 23, scale = 2)
    private BigDecimal planPaid;

    // 000.00
    @Column(columnDefinition = "NUMERIC(23,2)", name = "member_responsibility",
            nullable = false, precision = 23, scale = 2)
    private BigDecimal memberResponsibility;
}
