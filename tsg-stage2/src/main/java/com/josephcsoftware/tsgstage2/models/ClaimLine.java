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
    @Column(columnDefinition = "UUID", nullable = false, unique = true)
    private UUID id;

    @Column(columnDefinition = "UUID", nullable = false)
    private UUID claimId;

    @Size(min = 1)
    @Column(columnDefinition = "INTEGER", nullable = false)
    private Integer lineNumber;

    // A shorthand code for conditions and treatments
    @Column(columnDefinition = "TEXT", nullable = false)
    private String cptCode;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;
    
    // 000.00
    @Column(columnDefinition = "NUMERIC(23,2)",
            nullable = false, precision = 23, scale = 2)
    private BigDecimal billedAmount;

    // 000.00
    @Column(columnDefinition = "NUMERIC(23,2)",
            nullable = false, precision = 23, scale = 2)
    private BigDecimal allowedAmount;

    // 000.00
    @Column(columnDefinition = "NUMERIC(23,2)",
            nullable = false, precision = 23, scale = 2)
    private BigDecimal deductibleApplied;

    // 000.00
    @Column(columnDefinition = "NUMERIC(23,2)",
            nullable = false, precision = 23, scale = 2)
    private BigDecimal copayApplied;

    // 000.00
    @Column(columnDefinition = "NUMERIC(23,2)",
            nullable = false, precision = 23, scale = 2)
    private BigDecimal coinsuranceApplied;

    // 000.00
    @Column(columnDefinition = "NUMERIC(23,2)",
            nullable = false, precision = 23, scale = 2)
    private BigDecimal planPaid;

    // 000.00
    @Column(columnDefinition = "NUMERIC(23,2)",
            nullable = false, precision = 23, scale = 2)
    private BigDecimal memberResponsibility;
}
